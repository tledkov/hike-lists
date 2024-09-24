package io.github.tledkov.hikelists.ui.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.data.CategoryRepository
import io.github.tledkov.hikelists.data.InventoryItemRepository
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import java.io.Serializable

class InventoryViewModel(
    private val app: Application,
    private val inventoryItemRepository: InventoryItemRepository,
    private val categoryRepository: CategoryRepository
) : AndroidViewModel(app) {

    var tabs: MutableList<TabData> = mutableListOf()

    val categoriesLd: LiveData<List<Category>> = categoryRepository.getAllCategories().asLiveData()

    var allItems: MutableList<InventoryItem> = mutableListOf()
    val allItemsLd: LiveData<List<InventoryItem>> = inventoryItemRepository.getAllItems().asLiveData()

    fun updateCategories(categories: List<Category>) {
        tabs.clear()
        tabs.add(
            TabData(
                app.resources.getString(R.string.category_all_items)
            )
        )

        tabs.addAll(categories.map { TabData(it) })
    }

    fun updateItems(items: List<InventoryItem>) {
        allItems.clear()
        allItems = items.toMutableList()
    }


//    val inventory: MutableList<TabData>
//
//        init {
//            inventory = categoryRepository.getAllCategoriesF()
//                .zip(inventoryItemRepository.getAllItemsF()) { categories, allItems ->
//                    val inventory = Inventory(categories, allItems)
//
//                    val tabsData: MutableList<TabData> = mutableListOf()
//
//                    tabsData.add(
//                        TabData(
//                            MutableLiveData(inventory.allInventoryItems),
//                            app.resources.getString(R.string.category_all_items)
//                        )
//                    )
//
//                    for (cat in categories) {
//                        tabsData.add(TabData(MutableLiveData(inventory.itemsByCategory[cat]!!), cat))
//                    }
//
//                    tabsData.add(
//                        TabData(
//                            MutableLiveData(inventory.withoutCategoryItems),
//                            app.resources.getString(R.string.category_not_category)
//                        )
//                    )
//
//                    return@zip tabsData
//                }.toList()
//        }
//        get() = categoryRepository.getAllCategoriesF()
//            .zip(inventoryItemRepository.getAllItemsF()) { categories, allItems ->
//                val inventory = Inventory(categories, allItems)
//
//                val tabsData: MutableList<TabData> = mutableListOf()
//
//                tabsData.add(
//                    TabData(
//                        MutableLiveData(inventory.allInventoryItems),
//                        app.resources.getString(R.string.category_all_items)
//                    )
//                )
//
//                for (cat in categories) {
//                    tabsData.add(TabData(MutableLiveData(inventory.itemsByCategory[cat]!!), cat))
//                }
//
//                tabsData.add(
//                    TabData(
//                        MutableLiveData(inventory.withoutCategoryItems),
//                        app.resources.getString(R.string.category_not_category)
//                    )
//                )
//
//                return@zip tabsData
//            }.asLiveData()


    class TabData(
        private val category: Category?,
        private val name: String?
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