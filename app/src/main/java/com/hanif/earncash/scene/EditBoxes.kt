package com.hanif.earncash.scene

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hanif.earncash.DaO.UserDao
import com.hanif.earncash.Remote.sharePref
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import kotlinx.coroutines.launch


@Composable
fun EditBoxes(onSubmit:(String)->Unit) {
    val userData = UserDao()
    var name by remember { mutableStateOf(userData.name) }
    var email by remember {
        mutableStateOf(
            userData.email
        )
    }
    var phone by remember {
        mutableStateOf(
            userData.phoneNumber
        )
    }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("নাম") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                8.dp
            )
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("ই-মেইল") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                8.dp
            )
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("নাম্বার") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                8.dp
            )
        )

        Button(onClick = {
            userData.name = name
            userData.email = email
            userData.phoneNumber = phone
            sharePref().storeEmail(email, context)
            coroutine.launch {
                fireObject.storeUserData(userData)
            }
            onSubmit(email)

        }) {
            Text("সাবমিট")
        }

        Spacer(
            modifier = Modifier.height(
                20.dp
            )
        )

    }
}