package com.hanif.earncash.DaO

import kotlinx.serialization.Serializable


@Serializable
sealed class Route{
    @Serializable
    data class HomeScene(val email: String) : Route()
    @Serializable
    data class NonSubscribed(val emaail: String) : Route()
    @Serializable
    data class FiverOrder(val emaail: String) : Route()
    @Serializable
    data object SubcribedApp : Route()
    @Serializable
    data object ProfileScreen : Route()
    @Serializable
    data object SignUP : Route()

}
