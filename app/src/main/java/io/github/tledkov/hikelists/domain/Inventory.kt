package io.github.tledkov.hikelists.domain

import java.io.Serializable

class Inventory(
    val categories: List<Category>,
    val allInventoryItems: List<InventoryItem>
) : Serializable {
    val itemsByCategory: MutableMap<Category, MutableList<InventoryItem>> = mutableMapOf()
    val withoutCategoryItems: List<InventoryItem> = allInventoryItems.filter { it.category == null }

    init {
        for (cat in categories) {
            itemsByCategory.put(cat, mutableListOf())
        }

        for (item in allInventoryItems) {
            item.category?.let {
                itemsByCategory[it]!!.add(item)
            }
        }
    }
}