package io.github.tledkov.hikelists.ui.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.tledkov.hikelists.databinding.ListItemBinding
import io.github.tledkov.hikelists.domain.InventoryList

class ListAdapter(private val itemClickListener: ListViewHolder.OnItemClickListener) :
    RecyclerView.Adapter<ListViewHolder>() {

    private val lists = mutableListOf<InventoryList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: ListItemBinding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(itemClickListener, binding)
    }

    fun setLists(newLists: List<InventoryList>) {
        lists.clear()
        lists.addAll(newLists)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount() = lists.size
}
