package io.github.tledkov.hikelists.ui.inventory

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.ItemBinding
import io.github.tledkov.hikelists.domain.InventoryItem

class ItemViewHolder(
    private val itemClickListener: ItemAdapter.OnItemClickListener,
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: InventoryItem) {
        item.run {
            binding.itemNameText.text = name
            binding.itemDescriptionText.text = description
            binding.itemWeightText.text =
                itemView.context.resources.getString(R.string.weight_gram, weight.value())

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(it, item)
            }

            item.category?.color?.let {
                binding.itemSelectBtn.backgroundTintList = ColorStateList.valueOf(it.toArgb())
            }
        }
    }
}