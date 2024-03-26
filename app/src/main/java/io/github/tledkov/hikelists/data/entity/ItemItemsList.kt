package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.ItemItemsList.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class ItemItemsList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val listId: Int
) {

    companion object {
        const val TABLE_NAME = "item_items_list"
    }
}