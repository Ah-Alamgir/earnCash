package com.hanif.earncash.Utils

import android.app.AppOpsManager
import android.content.Context
import android.os.Process

class Permission {
    fun hasUsageStatsPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode =
            appOps.unsafeCheckOpNoThrow(
                "android:get_usage_stats",
                Process.myUid(),
                context.packageName
            )
        return mode == AppOpsManager.MODE_ALLOWED
    }
}