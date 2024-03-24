package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.ItemCategory.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class ItemCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val categoryId: Int
) {

    companion object {
        const val TABLE_NAME = "item_category"
    }
}