// ファイル名: ProductData.kt
package com.example.goodvalueapp

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ProductData(
    val name: String,
    val price: Double,
    val weight: Double
) : Parcelable {
    val pricePerGram: Double
        get() = if (weight > 0) price / weight else 0.0
}
