package com.hanif.earncash.scene

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.NonSubAppDao

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SubcribedApp(appListState: List<AppDao>) {
val context = LocalContext.current

    Column(modifier = Modifier.padding(2.dp, 10.dp, 2.dp, 2.dp)) {
        LazyColumn {
            items(appListState) { appDao ->
                AppItem(
                    NonSubAppDao(appDao.name, appDao.url, appDao.icon, appDao.packageName, 0,"")
                ) { packageName ->
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                }
            }
        }
    }
}


