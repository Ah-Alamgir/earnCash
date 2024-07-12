package com.hanif.earncash.scene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.Utils.ConfirmationDialogues


@Composable
fun FiverOrder() {
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }
    var packageName by remember { mutableStateOf("") }
    var subscriber by remember { mutableStateOf("") }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    var DialogueText = ""


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = icon,
            onValueChange = { icon = it },
            label = { Text("Icon") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = packageName,
            onValueChange = { packageName = it },
            label = { Text("Package Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = subscriber,
            onValueChange = { subscriber = it },
            label = { Text("Subscriber") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Handle submission of NonSubAppDao data
            val appOrder = NonSubAppDao(
                name = name,
                url = url,
                icon = icon,
                packageName = packageName,
                subscriber = subscriber.toIntOrNull() ?: 0 // Handle potential non-integer input

            )
//            AirtableApiClient().storeData(appOrder, scope)

       }){

            Text("Submit")
        }
    }
    if (showDialog){
        ConfirmationDialogues(DialogueText, onYesClicked = {
            showDialog = false
        })

    }
}