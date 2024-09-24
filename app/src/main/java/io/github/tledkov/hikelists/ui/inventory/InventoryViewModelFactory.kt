package io.github.tledkov.hikelists.ui.inventory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.data.CategoryRepository
import io.github.tledkov.hikelists.data.InventoryItemRepository

class InventoryViewModelFactory(
    private val app: Application,
    private val categoryRepository: CategoryRepository,
    private val inventoryItemRepository: InventoryItemRepository,
) : ViewModelProvider.AndroidViewModelFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return create(modelClass)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(app, inventoryItemRepository, categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}