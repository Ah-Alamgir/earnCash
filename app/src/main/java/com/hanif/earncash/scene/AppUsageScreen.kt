package com.hanif.earncash.scene

import AirtableApiClient
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.AppUsage
import com.hanif.earncash.Utils.FetchAppUses.fetchAppUsage


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppUsageScreen(appListState: List<AppDao>) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val client  = AirtableApiClient()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(appListState) {
            val time = fetchAppUsage(context, it.packageName)
            AppUsageItem(AppUsage(it.name, it.icon, time,), client)

        }
    }

}


@Composable
fun AppUsageItem(appUsage: AppUsage, client: AirtableApiClient) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .size(55.dp)
        ) {
            AsyncImage(
                model = appUsage.appIconUrl,
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Column {
                Text(text = appUsage.appName)
                Spacer(modifier = Modifier.height(8.dp))
                if (appUsage.usageTimeInSeconds >= 2.0) {
//                    client.appTested(appUsage.appName)
                    LinearProgressIndicator(progress = { 2.0f })

                } else {
                    val scaledProgress = appUsage.usageTimeInSeconds / 2.0f
                    LinearProgressIndicator(
                        progress = { scaledProgress },
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "${appUsage.usageTimeInSeconds} মিনিট বব্যাবহার করেছেন")

            }
        }

    }
    Spacer(modifier = Modifier.height(3.dp))
}