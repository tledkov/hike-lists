package io.github.tledkov.hikelists.domain

import java.io.Serializable

data class InventoryItem(
    val id: Int = 0,
    val category: Category? = null,
    val weightGr: Int,
    val name: String,
    val description: String = "",
    val img: String = ""
) : Serializable