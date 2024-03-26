package io.github.tledkov.hikelists.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Please add some inventory to create backpack lists"
    }
    val text: LiveData<String> = _text
}