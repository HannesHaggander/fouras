package com.nattfall.fouras.home.data

import androidx.annotation.DrawableRes

data class ProductData(
    val id: String,
    val name: String,
    @DrawableRes val image: Int,
    val description: String,
    val price: Float,
    val currency: String = "SEK",
)
