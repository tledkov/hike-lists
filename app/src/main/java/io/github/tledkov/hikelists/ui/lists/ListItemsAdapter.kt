package io.github.tledkov.hikelists.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.databinding.ListItemElementBinding
import io.github.tledkov.hikelists.domain.InventoryList

class ListItemsAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ListItemsAdapter.ListItemViewHolder>() {

    private val itemsList = mutableListOf<InventoryList.UsedItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding: ListItemElementBinding = ListItemElementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListItemViewHolder(binding, itemClickListener)
    }

    fun setItems(items: List<InventoryList.UsedItem>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItemChecked(position: Int, isChecked: Boolean) {
        itemsList[position] = itemsList[position].copy(checked = isChecked)
    }

    fun removeItemAt(position: Int) {
        itemsList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: InventoryList.UsedItem) {
        itemsList.add(item)
        notifyItemInserted(itemsList.size - 1)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount() = itemsList.size

    fun getItemAt(position: Int): InventoryList.UsedItem = itemsList[position]

    class ListItemViewHolder(
        private val binding: ListItemElementBinding,
        private val itemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(usedItem: InventoryList.UsedItem) {
            binding.itemElementName.text = usedItem.item.name
            binding.itemElementCategory.text = usedItem.item.category?.name ?: ""
            binding.itemElementWeight.text = binding.root.context.getString(
                io.github.tledkov.hikelists.R.string.weight_gram,
                usedItem.item.weight.weightGr
            )
            binding.itemElementCount.text = "×${usedItem.count}"
            binding.itemElementCheckbox.isChecked = usedItem.checked

            binding.itemElementCheckbox.setOnCheckedChangeListener { _, isChecked ->
                itemClickListener.onItemChecked(adapterPosition, isChecked)
            }

            binding.itemElementRemoveBtn.setOnClickListener {
                itemClickListener.onItemRemoveClicked(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemChecked(position: Int, isChecked: Boolean)
        fun onItemRemoveClicked(position: Int)
    }
}
