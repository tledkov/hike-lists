package io.github.tledkov.hikelists.ui.lists

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.databinding.ListItemBinding
import io.github.tledkov.hikelists.domain.InventoryList

class ListViewHolder(
    private val itemClickListener: OnItemClickListener,
    private val binding: ListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(inventoryList: InventoryList) {
        inventoryList.run {
            binding.listItemName.text = name
            binding.listItemDescription.text = description

            val totalWeightGrams = weight()
            val weightKg = totalWeightGrams / 1000.0
            binding.listItemWeight.text = String.format("%.1f kg", weightKg)

            binding.listItemCount.text = "${items.size} items"

            binding.listItemColor.setBackgroundColor(color.toArgb())

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(it, inventoryList)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(view: android.view.View, item: InventoryList)
    }
}
