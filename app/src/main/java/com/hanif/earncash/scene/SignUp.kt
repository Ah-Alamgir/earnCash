package com.hanif.earncash.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hanif.earncash.R
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject

@Composable
fun SignUP(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 60.dp, 10.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = null,
            Modifier
                .clip(CircleShape)
                .size(150.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            onClick = { /*TODO*/ },
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            EditBoxes {email->
                fireObject.email = email
                fireObject.storEanring(0)
                navController.navigate("home")
            }
        }
    }


}