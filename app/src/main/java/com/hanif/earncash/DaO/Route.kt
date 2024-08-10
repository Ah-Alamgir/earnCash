package com.hanif.earncash.DaO

import kotlinx.serialization.Serializable


@Serializable
sealed class Route{
    @Serializable
    data class HomeScene(val email: String) : Route()
    @Serializable
    data class NonSubscribed(val email: String) : Route()
    @Serializable
    data class FiverOrder(val email: String) : Route()
    @Serializable
    data object SubcribedApp : Route()
    @Serializable
    data class ProfileScreen(val email: String, val earning:String) : Route()
    @Serializable
    data object SignUP : Route()
    @Serializable
    data object airtTableView : Route()

}
