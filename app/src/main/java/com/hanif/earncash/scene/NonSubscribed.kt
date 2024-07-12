package com.hanif.earncash.scene

import AirtableApiClient
import android.annotation.SuppressLint
import android.util.Log
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
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import com.hanif.earncash.Utils.ConfirmationDialogues
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NonsubsCribed(args: Route.NonSubscribed) {

    var nonSubApps by remember { mutableStateOf<List<NonSubAppDao>>(emptyList()) } // Initialize as empty list

    LaunchedEffect(Unit) {
        fireObject.getListedApps().collect { apps ->
            if (apps != null) {
                nonSubApps = apps
            }
        }
    }

    val Applist = AirtableApiClient()


    var showDialogue by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(10.dp, 40.dp, 10.dp, 20.dp)) {
        Text(text = "এপ এর উপর ক্লিক করুন")
        LazyColumn {
            items(nonSubApps) { dao ->
                AppItem(appInfo = dao) { packageName ->
//                    val isDone = fireObject.storeSubApp(
//                        AppDao(
//                            dao.name, dao.url, dao.icon, dao.packageName, 0, 0
//                        )
//                    )
                    Applist.storeStrings(args.email, dao.name, object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d("luckey", dao.name)

                            Log.d("luckey", response.code.toString())
                            if(response.code == 200){
                                showDialogue = true
                            }
                        }

                    })

                }
            }
        }
        if (showDialogue) {
            ConfirmationDialogues("এখন থেকে এপটি টেস্ট করতে পারবেন", onYesClicked = {
                showDialogue = false
            })
        }
    }
}


