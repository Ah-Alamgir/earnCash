package com.hanif.earncash.scene

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hanif.earncash.DaO.NonSubAppDao

@Composable
fun AppItem(appInfo: NonSubAppDao, onAppclick: (NonSubAppDao)->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp)
            .clickable {
                onAppclick(appInfo)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = appInfo.icon,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp),// Load the icon from the URL
                contentDescription = "App Icon",// Adjust size as needed
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    appInfo.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    appInfo.packageName,
                    style = MaterialTheme.typography.bodySmall
                )
            }


        }
    }
}