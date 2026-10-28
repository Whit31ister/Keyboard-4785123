package org.fossify.keyboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import org.fossify.keyboard.databinding.ItemGifBinding
import org.fossify.keyboard.models.GifItem

class GifsAdapter(
    private val onClick: (GifItem) -> Unit
) : RecyclerView.Adapter<GifsAdapter.ViewHolder>() {
    private var items = emptyList<GifItem>()

    fun submitList(items: List<GifItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GifItem) {
            Glide.with(binding.gifPreview).load(item.previewUrl).centerCrop().into(binding.gifPreview)
            binding.root.contentDescription = item.title
            binding.root.setOnClickListener { onClick(item) }
        }
    }
}
