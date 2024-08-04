package com.hanif.earncash.DaO

import kotlinx.serialization.Serializable


@Serializable
data class SubscribedDao(
    val name: String,
    val url: String,
    val icon: String,
    val packageName: String,
) {
    constructor() : this("", "", "", "" )
}
