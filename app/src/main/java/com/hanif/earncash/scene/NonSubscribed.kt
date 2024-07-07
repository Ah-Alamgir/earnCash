package com.hanif.earncash.scene

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import com.hanif.earncash.Utils.ConfirmationDialogues

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NonsubsCribed(context: Context) {

    var nonSubApps by remember { mutableStateOf<List<NonSubAppDao>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {

    }

    var showDialogue by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 20.dp)) {
        Text(text = "এপ এর উপর ক্লিক করুন")
        LazyColumn {
            items(nonSubApps) { dao->
                AppItem(appInfo = dao) { packageName ->
                    val isDone = fireObject.storeSubApp(AppDao(dao.name, dao.url, dao.icon, dao.packageName, 0,0))
                    if (isDone){
                        showDialogue = true
                }
            }
        }
    }
    if (showDialogue){
        ConfirmationDialogues("এখন থেকে এপটি টেস্ট করতে পারবেন", onYesClicked = {
            showDialogue = false
        },
            onDismissRequest = {
                showDialogue = false
            })
    }
    }
}


