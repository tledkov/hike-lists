package io.github.tledkov.hikelists.ui.inventory

import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.databinding.ItemBinding

class ItemViewHolder(
    private val itemClickListener: ItemAdapter.OnItemClickListener,
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemEntity: ItemEntity) {
        itemEntity.run {
            binding.nameTextView.text = name
            binding.descriptionTextView.text = description

            binding.root.setOnClickListener {
                itemClickListener.onItemClicked(itemEntity)
            }
        }
    }
}