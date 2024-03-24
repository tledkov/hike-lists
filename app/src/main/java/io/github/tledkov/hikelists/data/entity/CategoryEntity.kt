package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.CategoryEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val color: String
) {
    companion object {
        const val TABLE_NAME = "category"
        const val ID = "id"
    }
}