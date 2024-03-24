package io.github.tledkov.hikelists.domain

import android.graphics.Color
import java.io.Serializable

data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val color: Color
) : Serializable