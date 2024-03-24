package io.github.tledkov.hikelists.domain

import java.io.Serializable

class Inventory(
    categories: List<Category>,
    allInventoryItems: List<InventoryItem>
) : Serializable {
    val itemsByCategory: MutableMap<Category, MutableList<InventoryItem>> = mutableMapOf()
    val itemsWithoutCategory: List<InventoryItem> = allInventoryItems.filter { it.category == null }

    init {
        for (item in allInventoryItems) {
            item.category?.let {
                itemsByCategory.computeIfAbsent(it) { mutableListOf() }.add(item)
            }
        }
    }
}