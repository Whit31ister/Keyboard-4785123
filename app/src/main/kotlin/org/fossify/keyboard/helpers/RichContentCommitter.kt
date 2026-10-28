package org.fossify.keyboard.helpers

import android.content.ClipDescription
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.core.content.FileProvider
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputContentInfoCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.fossify.keyboard.models.GifItem

class RichContentCommitter(
    private val context: Context,
    private val repository: GifRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    fun commit(item: GifItem, inputConnection: InputConnection?, editorInfo: EditorInfo?) {
        if (inputConnection == null || editorInfo == null) return
        scope.launch {
            val result = runCatching {
                val file = repository.download(item)
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.gifprovider", file)
                val supportsGif = EditorInfoCompat.getContentMimeTypes(editorInfo)
                    .any { ClipDescription.compareMimeTypes(it, "image/gif") }
                withContext(Dispatchers.Main) {
                    if (supportsGif) {
                        val description = ClipDescription(item.title.ifBlank { "GIF" }, arrayOf("image/gif"))
                        val contentInfo = InputContentInfoCompat(uri, description, null)
                        val committed = InputConnectionCompat.commitContent(
                            inputConnection,
                            editorInfo,
                            contentInfo,
                            InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION,
                            null
                        )
                        if (!committed) inputConnection.commitText(item.contentUrl, 1)
                    } else {
                        inputConnection.commitText(item.contentUrl, 1)
                    }
                }
            }
            result.onFailure {
                if (it is CancellationException) throw it
                withContext(Dispatchers.Main) {
                    inputConnection.commitText(item.contentUrl, 1)
                }
            }
        }
    }

    fun close() {
        scope.coroutineContext[Job]?.cancel()
    }
}
