package com.hanif.earncash.scene

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hanif.earncash.Airtable.MyViewModel
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.Utils.ConfirmationDialogues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NonsubsCribed(args: Route.NonSubscribed) {
    val viewModel = MyViewModel()

    var dialogueText by remember {
        mutableStateOf("")
    }
    var nonSubApps by remember { mutableStateOf<List<NonSubAppDao>>(emptyList()) } // Initialize as empty list

    val apiresult = viewModel.apiResultSubscribed.collectAsState()

    var showDialogue by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(apiresult) {
        withContext(Dispatchers.IO) {

        }



    }



    Column(modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 20.dp)) {
        Text(text = "এপ এর উপর ক্লিক করুন")
        LazyColumn {
            items(nonSubApps) { dao ->
                AppItem(appInfo = dao) {
                    val appdao = mapOf(
                        "fields" to mapOf(
                            "tiktok" to "ahalamgir64@gmail.com",
                            "c" to "tiktok" // Replace 'c' with the actual field name in your Airtable base
                        ))
//                    viewModel.storNonSub("https://api.airtable.com/v0/appCOC2vF3Cu4auYI/Applist", appdao)

                }
            }
        }

    }


    if (showDialogue) {
        ConfirmationDialogues(dialogueText)
    }


}



