package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.ItemsListEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class ItemsListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String
) {

    companion object {
        const val TABLE_NAME = "items_list"
    }
}