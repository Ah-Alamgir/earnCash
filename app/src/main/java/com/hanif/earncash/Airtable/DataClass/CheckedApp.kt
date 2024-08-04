package com.hanif.earncash.Airtable.DataClass

data class CheckedApp(
    val id: String,
    val createdTime: String,
    val fields: Map<String, String>
)

data class CheckAppList(
    val records: List<CheckedApp>
) // While the app is first running getting the date and tested app list. so that we can check how many app the user had tested.