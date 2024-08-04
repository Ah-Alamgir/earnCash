package com.hanif.earncash.scene

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun HandlePermission(context: Context) {
    println("we are here")
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

        AlertDialog(
            onDismissRequest = {
                showDialogue = false
            },
            confirmButton = {
                OutlinedButton(onClick = {
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    launcher.launch(intent)
                }) {
                    Text(text = "OK")
                }

            },
            title = { Text(text = "অনুমতি দিন") },
            text = { Text(text = "অনুমতি না দিলে কাজ করা যাবে না") },
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