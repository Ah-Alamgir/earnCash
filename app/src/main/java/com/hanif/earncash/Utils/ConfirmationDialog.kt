package com.hanif.earncash.Utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ConfirmationDialogues(
    message: String,
    onYesClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,// You can customize the title
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onYesClicked) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("No")
            }
        }
    )
}