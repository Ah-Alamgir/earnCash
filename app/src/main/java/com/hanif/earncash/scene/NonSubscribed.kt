package com.hanif.earncash.scene

import AirtableApiClient
import android.annotation.SuppressLint
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
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import com.hanif.earncash.Utils.ConfirmationDialogues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NonsubsCribed(args: Route.NonSubscribed) {

    var nonSubApps by remember { mutableStateOf<List<NonSubAppDao>>(emptyList()) } // Initialize as empty list
    var dialogueText by remember {
        mutableStateOf("")
    }

    var showDialogue by remember {
        mutableStateOf(false)
    }
    val airtableData = AirtableApiClient()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            airtableData.getListedApps().collect { apps ->
                apps.fold(
                    onSuccess = {
                        nonSubApps= it
                    },
                    onFailure = {
                        dialogueText = it.message.toString()
                        showDialogue = true
                    }
                )
            }
        }

    }
    if (showDialogue){
        ConfirmationDialogues(dialogueText)
    }


    Column(modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 20.dp)) {
        Text(text = "এপ এর উপর ক্লিক করুন")
        LazyColumn {
            items(nonSubApps) { dao ->
                AppItem(appInfo = dao) { packageName ->
                    val isDone = fireObject.storeSubApp(
                        AppDao(
                            dao.name, dao.url, dao.icon, dao.packageName, 0, 0
                        )
                    )


                }
            }
        }
        if (showDialogue) {
            ConfirmationDialogues("এখন থেকে এপটি টেস্ট করতে পারবেন")
        }
    }
}


