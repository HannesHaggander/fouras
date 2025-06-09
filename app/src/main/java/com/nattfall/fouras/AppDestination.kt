package com.nattfall.fouras

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
    @Serializable
    data object Authentication : AppDestination

    @Serializable
    data object Home : AppDestination

    @Serializable
    data class ProductDetail(val id: String) : AppDestination

    @Serializable
    data object Checkout : AppDestination

    @Serializable
    data object Settings : AppDestination
}