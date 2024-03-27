package io.github.tledkov.hikelists.ui.inventory

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
            binding.nameTextView.text = name
            binding.descriptionTextView.text = description
            binding.weightTextView.text =
                itemView.context.resources.getString(R.string.weight_gram, weight.value())

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(item)
            }
        }
    }
}