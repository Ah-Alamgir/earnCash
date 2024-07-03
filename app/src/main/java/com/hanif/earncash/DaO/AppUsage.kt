package com.hanif.earncash.DaO

data class AppUsage(
    val appName: String,
    val appIconUrl: String,
    val usageTimeInSeconds: Int // Store usage time in seconds
)
