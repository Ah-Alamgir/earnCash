package com.hanif.earncash.scene

import AirtableApiClient
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.R
import com.hanif.earncash.Remote.RealtimeDatabaseRepository
import com.hanif.earncash.Remote.sharePref
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import com.hanif.earncash.Utils.ConfirmationDialogues
import com.hanif.earncash.Utils.Permission
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScene(args:Route.HomeScene, changeScene: (String)-> Unit) {
    var subscribedApps by remember { mutableStateOf<List<AppDao>?>(null) }
    var dataLoaded by remember { mutableStateOf(false) }
    val earnClass = RealtimeDatabaseRepository()
    val earningsFlow = earnClass.getEarningPointsFlow(args.email) // Get the Flow
    val earning by earningsFlow.collectAsState(initial = 0)
    val context = LocalContext.current
    HandlePermission(context)



    if (args.email == "no") {
        sharePref().getEmail(context)
    }
    LaunchedEffect(key1 = dataLoaded) {
        if (!dataLoaded) {

            fireObject.getSubscribedApps().collect { apps ->
                subscribedApps = apps
            }
            dataLoaded = true
        }
        AirtableApiClient().storeStrings("Sandi","fldsyaVUJYAFosDcB", object :  Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("luckey", e.cause.toString())

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("luckey", response.code.toString())
            }

        })


    }





    Column(modifier = Modifier.padding(10.dp, 30.dp, 10.dp, 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(painter = painterResource(id = R.mipmap.coin),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = earning.toString(), fontSize = 45.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(painter = painterResource(id = R.mipmap.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    })
        }

        Text(text = "কমপক্ষে ২ মিনিট ব্যাবহার করুন")
        if (subscribedApps != null) {
            AppUsageScreen(subscribedApps!!)
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "আপনার লিস্টেড এপস", modifier = Modifier.align(Alignment.Bottom))
                Spacer(modifier = Modifier.weight(1f))

                Image(painter = painterResource(id = R.mipmap.game),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            changeScene(args.email)
                        })
            }
            SubcribedApp(subscribedApps!!)
        } else {
            // Show a loading indicator or an empty state message
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally)) // Or a Text composable with "Loading..."
        }
    }

}


@Composable
fun HandlePermission(context: Context) {
    var showDialogue by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (!Permission().hasUsageStatsPermission(context)) {
            showDialogue = true
        }
    }

    if (!Permission().hasUsageStatsPermission(context)) {
        showDialogue = true
    }

    if (showDialogue) {
        ConfirmationDialogues("পারমিশন দিন", onYesClicked = {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            launcher.launch(intent)
        }, onDismissRequest = {
            showDialogue = false
        })
    }
}