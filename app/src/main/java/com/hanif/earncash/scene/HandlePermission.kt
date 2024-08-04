package com.hanif.earncash.scene

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.hanif.earncash.R


@Composable
fun HandlePermission(context: Context) {
    var showDialogue by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (!hasPermission(context)) {
            showDialogue = true
        }
    }

    if (!hasPermission(context)) {
        showDialogue = true
    }

    if (showDialogue) {

        AlertDialog(onDismissRequest = {
            showDialogue = false
        }, confirmButton = {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            launcher.launch(intent)
        },
            title = { Text(text = "অনুমতি দিন") },
            text = { Text(text = "অনুমতি না দিলে কাজ করা যাবে না") },
            icon = {
                Icon(
                    painter = painterResource(id = R.mipmap.ic_launcher),
                    contentDescription = null
                )
            }
        )

    }
}


fun hasPermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode =
        appOps.unsafeCheckOpNoThrow(
            "android:get_usage_stats",
            Process.myUid(),
            context.packageName
        )
    return mode == AppOpsManager.MODE_ALLOWED
}