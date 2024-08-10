package com.hanif.earncash.scene

import AirtableApiClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.hanif.earncash.Remote.sharePref
import com.hanif.earncash.Utils.ConfirmationDialogues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScene(args: Route.HomeScene, onClick: (Route) -> Unit) {

    var subscribedApps by remember { mutableStateOf<List<AppDao>?>(null) }
    var dataLoaded by remember { mutableStateOf("Waiting for data") }
    var earning by remember {
        mutableStateOf("0")
    }
    var dialogueText by remember {
        mutableStateOf("")
    }

    var showDialogue by remember {
        mutableStateOf(false)
    }
    val airtableDb= AirtableApiClient()
//    airtableDb.convertToText()

    val context = LocalContext.current
    HandlePermission(context)

    if (args.email == "no") {
        sharePref().getEmail(context)
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            airtableDb.getCheckedApp("Apps?view=checkedToday&fields%5B%5D=c").collect {response->
                response.fold(
                    onSuccess = {
                    },
                    onFailure = {
                        dialogueText = it.message.toString()
                        showDialogue = true
                    }
                )

            }
//            earnClass.getEarning(args.email).collect { points ->
//                earning = points
//            }
        }
    }

    if (showDialogue){
        ConfirmationDialogues(dialogueText)
    }





    Column(modifier = Modifier.padding(10.dp, 30.dp, 10.dp, 20.dp)) {
        Text(text = dataLoaded)
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.mipmap.coin),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = earning, fontSize = 45.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(painter = painterResource(id = R.mipmap.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onClick(Route.ProfileScreen(args.email, earning))
                    })
            Spacer(modifier = Modifier.weight(.1f))

            Image(painter = painterResource(id = R.mipmap.game),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onClick(Route.NonSubscribed(email = args.email))
                    })
        }

        Text(text = "কমপক্ষে ২ মিনিট ব্যাবহার করুন")
        if (subscribedApps != null) {
            AppUsageScreen(subscribedApps!!)
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "আপনার লিস্টেড এপস", modifier = Modifier.align(Alignment.Bottom))
            }
            SubcribedApp(subscribedApps!!)
        } else {
            // Show a loading indicator or an empty state message
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally)) // Or a Text composable with "Loading..."
        }
    }
}




