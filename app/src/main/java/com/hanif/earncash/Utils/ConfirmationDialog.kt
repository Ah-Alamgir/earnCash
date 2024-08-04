package com.hanif.earncash.Utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun ConfirmationDialogues(
    message: String,
) {
    var showDialogue by remember {
        mutableStateOf(true)
    }
    if (showDialogue){
        AlertDialog(
            onDismissRequest = { showDialogue = false },// You can customize the title
            text = { Text(message) },
            confirmButton = {
                Button(onClick = { showDialogue = false }) {
                    Text("Ok")
                }
            }

        )
    }

}