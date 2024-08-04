package com.hanif.earncash.DaO



data class ApiResponse(
    val records: List<Record>
)

data class Record(
    val fields: Fields
)

data class Fields(
    val OrderdApps: String
)

data class NonSubAppDao(
    val name: String,
    val url: String,
    val icon: String,
    val packageName: String,
    val subscriber: Int = 0,
    val id: String
) {
    constructor() : this("", "", "", "", 0, "")
}