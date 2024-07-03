package com.hanif.earncash.DaO

data class NonSubAppDao(
    val name: String,
    val url: String,
    val icon: String,
    val packageName: String,
    val subscriber:Int= 0
) {
    constructor() : this("", "", "", "", 0 )
}
