package com.hanif.earncash.Utils

import android.app.usage.UsageStatsManager
import android.content.Context
import android.icu.util.Calendar


object FetchAppUses{
    fun fetchAppUsage(context: Context, targetapp:String): Int {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis

        val endTime = System.currentTimeMillis()

        val queryUsageStats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        val usageStats = queryUsageStats.find { it.packageName == targetapp }
        return usageStats?.let { (it.totalTimeInForeground / 1000 / 60).toInt() } ?: 0
    }
}
