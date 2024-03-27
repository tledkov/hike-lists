package io.github.tledkov.hikelists.domain

import android.icu.util.MeasureUnit
import java.io.Serializable
import java.lang.RuntimeException

data class InventoryItem(
    val id: Int = 0,
    val category: Category? = null,
    val weight: Weight,
    val name: String,
    val description: String = "",
    val img: String = ""
) : Serializable

data class Weight (val weightGr: Int) {
    fun value(unit: MeasureUnit = MeasureUnit.GRAM) : Int {
        return when (unit) {
            MeasureUnit.GRAM -> weightGr
            MeasureUnit.POUND -> (weightGr / 453.592).toInt()
            MeasureUnit.OUNCE -> (weightGr / 28.3495).toInt()
            else -> {throw RuntimeException("Invalid weight unit $unit")}
        }
    }

    companion object {
        fun from(value: Int, unit: MeasureUnit = MeasureUnit.GRAM) : Weight {
            val w: Int = Math.abs(value)
            return when (unit) {
                MeasureUnit.GRAM -> Weight(w)
                MeasureUnit.POUND -> Weight((w * 453.592).toInt())
                MeasureUnit.OUNCE -> Weight((w * 28.3495).toInt())
                else -> {throw RuntimeException("Invalid weight unit $unit")}
            }
        }
    }
}