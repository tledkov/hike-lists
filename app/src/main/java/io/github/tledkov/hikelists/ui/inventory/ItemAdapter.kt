package io.github.tledkov.hikelists.ui.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.databinding.ItemBinding
import io.github.tledkov.hikelists.domain.InventoryItem

class ItemAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ItemViewHolder>() {

    private val itemsList = mutableListOf<InventoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemBinding = ItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ItemViewHolder(itemClickListener, binding)
    }

    fun addItem(itemEntity: InventoryItem) {
        itemsList.add(itemEntity)
        notifyDataSetChanged()
    }

    fun setItems(itemEntities: List<InventoryItem>) {
        itemsList.clear()
        itemsList.addAll(itemEntities)
        notifyDataSetChanged()
    }

    fun removeItem(id: Int) {
        val personToRemove = itemsList.find { it.id == id }
        personToRemove?.let {
            itemsList.remove(it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount() = itemsList.size

    interface OnItemClickListener {
        fun onItemClicked(item: InventoryItem)
    }
}