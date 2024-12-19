package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class RelationItemToItemsListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val listId: Int,
    val count: Int,
    val checked: Boolean,
) {

    companion object {
        const val TABLE_NAME = "relation_item_to_items_list"
        const val ID = "id"
    }
}