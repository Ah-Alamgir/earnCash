package com.hanif.earncash.DaO


data class AppDao(
    val name: String = "",
    val url: String = "",
    val icon: String = "",
    val packageName: String = "",
    val usedTime: Int = 0,
    val CheckedDay: Int = 0
){
    constructor() : this("", "", "", "", 0,0 )
}