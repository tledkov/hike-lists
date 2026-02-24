package io.github.tledkov.hikelists.ui.lists

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.github.tledkov.hikelists.data.InventoryListRepository
import io.github.tledkov.hikelists.domain.InventoryList
import kotlinx.coroutines.launch

class ListsViewModel(
    private val app: Application,
    private val inventoryListRepository: InventoryListRepository,
) : AndroidViewModel(app) {

    val listLd: LiveData<List<InventoryList>> = inventoryListRepository.getAllInventoryLists().asLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "Create backpack lists"
    }
    val text: LiveData<String> = _text

    fun upsertList(inventoryList: InventoryList) {
        viewModelScope.launch {
            inventoryListRepository.upsert(inventoryList)
        }
    }

    fun deleteList(inventoryList: InventoryList) {
        viewModelScope.launch {
            inventoryListRepository.delete(inventoryList)
        }
    }
}