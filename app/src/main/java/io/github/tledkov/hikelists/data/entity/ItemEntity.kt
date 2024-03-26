package io.github.tledkov.hikelists.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.tledkov.hikelists.data.entity.ItemEntity.Companion.TABLE_NAME
import java.io.Serializable

typealias ItemId = Int

@Entity(
    tableName = TABLE_NAME
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int?,
    val weightGr: Int,
    val name: String,
    val description: String = "",
    val img: String = ""
) : Serializable {

    companion object {
        const val TABLE_NAME = "item"

        const val ID = "id"
    }
}