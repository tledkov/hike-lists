package io.github.tledkov.hikelists.domain

import android.graphics.Color
import java.io.Serializable

data class InventoryList(
    val id: Int = 0,
    val name: String,
    val description: String,
    val items: MutableList<UsedItem>,
    val color: Color,
) : Serializable {
    fun weight(): Int {
        return items.sumOf { it.item.weight.value() * it.count }
    }

    data class UsedItem (
        val item: InventoryItem,
        val count: Int,
        val checked: Boolean,
    )
}



