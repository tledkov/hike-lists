package io.github.tledkov.hikelists.ui.lists

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.data.CategoryRepository
import io.github.tledkov.hikelists.data.InventoryItemRepository
import io.github.tledkov.hikelists.data.InventoryListRepository

class ListsViewModelFactory(
    private val app: Application,
    private val inventoryListRepository: InventoryListRepository,
) : ViewModelProvider.AndroidViewModelFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return create(modelClass)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListsViewModel(app, inventoryListRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}