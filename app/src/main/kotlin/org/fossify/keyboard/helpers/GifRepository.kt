package org.fossify.keyboard.helpers

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.fossify.keyboard.BuildConfig
import org.fossify.keyboard.models.GifItem
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URLEncoder

class GifRepository(context: Context) {
    private val cacheDir = File(context.cacheDir, "gifs").apply { mkdirs() }
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var requestJob: Job? = null

    fun search(query: String, onResult: (Result<List<GifItem>>) -> Unit) {
        requestJob?.cancel()
        requestJob = scope.launch {
            val result = runCatching {
                require(BuildConfig.GIF_API_KEY.isNotBlank()) { "GIF search is not configured for this build." }
                val endpoint = if (query.isBlank()) "featured" else "search"
                val queryParams = buildString {
                    append("key=").append(encode(BuildConfig.GIF_API_KEY))
                    append("&client_key=fossify_keyboard&limit=24&media_filter=tinygif,gif")
                    if (query.isNotBlank()) append("&q=").append(encode(query))
                }
                val connection = (java.net.URL("https://tenor.googleapis.com/v2/$endpoint?$queryParams")
                    .openConnection() as HttpURLConnection).apply {
                    connectTimeout = 8_000
                    readTimeout = 10_000
                    requestMethod = "GET"
                }
                try {
                    if (connection.responseCode !in 200..299) {
                        error("GIF provider returned HTTP ${connection.responseCode}")
                    }
                    parseResults(connection.inputStream.bufferedReader().use { it.readText() })
                } finally {
                    connection.disconnect()
                }
            }
            withContext(Dispatchers.Main) { onResult(result) }
        }
    }

    suspend fun download(item: GifItem): File = withContext(Dispatchers.IO) {
        val target = File(cacheDir, "${item.contentUrl.hashCode()}.gif")
        if (target.exists() && target.length() > 0) return@withContext target

        val connection = (java.net.URL(item.contentUrl).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8_000
            readTimeout = 15_000
            requestMethod = "GET"
        }
        try {
            if (connection.responseCode !in 200..299) error("Unable to download GIF")
            if (connection.contentLengthLong > MAX_GIF_BYTES) error("GIF is too large")
            FileOutputStream(target).use { output ->
                connection.inputStream.use { input ->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var total = 0L
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        total += read
                        if (total > MAX_GIF_BYTES) error("GIF is too large")
                        output.write(buffer, 0, read)
                    }
                }
            }
            target
        } catch (error: Exception) {
            target.delete()
            throw error
        } finally {
            connection.disconnect()
        }
    }

    fun close() {
        requestJob?.cancel()
        scope.coroutineContext[Job]?.cancel()
    }

    private fun parseResults(json: String): List<GifItem> {
        val results = JSONObject(json).optJSONArray("results") ?: return emptyList()
        return buildList {
            for (index in 0 until results.length()) {
                val result = results.optJSONObject(index) ?: continue
                val formats = result.optJSONObject("media_formats") ?: continue
                val preview = formats.optJSONObject("tinygif")?.optString("url").orEmpty()
                val content = formats.optJSONObject("gif")?.optString("url").orEmpty()
                if (preview.isNotBlank() && content.isNotBlank()) {
                    add(GifItem(preview, content, result.optString("content_description")))
                }
            }
        }
    }

    private fun encode(value: String) = URLEncoder.encode(value, Charsets.UTF_8.name())

    companion object {
        private const val MAX_GIF_BYTES = 8L * 1024 * 1024
    }
}
