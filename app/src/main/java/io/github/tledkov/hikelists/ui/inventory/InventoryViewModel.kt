package io.github.tledkov.hikelists.ui.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.data.CategoryRepository
import io.github.tledkov.hikelists.data.InventoryItemRepository
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import kotlinx.coroutines.launch
import java.io.Serializable

class InventoryViewModel(
    private val app: Application,
    private val inventoryItemRepository: InventoryItemRepository,
    private val categoryRepository: CategoryRepository
) : AndroidViewModel(app) {

    var tabs: MutableList<TabData> = mutableListOf()
    var tabsByCategoryId: MutableMap<Int, TabData> = mutableMapOf()

    var categories: MutableList<Category> = mutableListOf()
    val categoriesLd: LiveData<List<Category>> = categoryRepository.getAllCategories().asLiveData()

    var allItems: MutableList<InventoryItem> = mutableListOf()
    val allItemsLd: LiveData<List<InventoryItem>> =
        inventoryItemRepository.getAllItems().asLiveData()

    fun updateCategories(cats: List<Category>) {
        categories = cats.toMutableList()

        updateTabs()
    }

    private fun updateTabs() {
        tabs.clear()
        tabsByCategoryId.clear()

        tabs.add(
            TabData(
                app.resources.getString(R.string.category_all_items)
            )
        )

        tabs.addAll(categories.map { TabData(it) })
        tabsByCategoryId.putAll(
            tabs
                .filter { it.category != null }
                .map { it.category!!.id to it }
        )

        // Handle items
        tabs.first().items.addAll(allItems)

        allItems.forEach { item ->
            item.category?.let {
                tabsByCategoryId[it.id]?.items?.add(item)
            }
        }
    }

    fun updateAllItems(items: List<InventoryItem>) {
        allItems = items.toMutableList()

        updateTabs()
    }

    fun upsertItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryItemRepository.upsert(item)
        }
    }

    class TabData(
        val category: Category?,
        val name: String?,
        val items: MutableList<InventoryItem> = mutableListOf()
    ) : Serializable {
        constructor(category: Category) : this(
            category,
            null
        )

        constructor(name: String) : this(
            null,
            name
        )

        fun name(): String {
            return if (name != null) {
                return name
            } else {
                category!!.name
            }
        }
    }
}