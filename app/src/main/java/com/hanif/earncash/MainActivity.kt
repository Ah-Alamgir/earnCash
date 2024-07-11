package com.hanif.earncash

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.Remote.sharePref
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import com.hanif.earncash.ui.theme.EarnCashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EarnCashTheme {
                val email = sharePref().getEmail(this)
                Navigation(this, email)
            }
        }
    }


}


@Composable
fun Navigation(context: Context, email: String) {
    val navController = rememberNavController()
    var destination:Route = Route.HomeScene(email)
    if (email == "no") {
        destination = Route.SignUP
    } else {
        fireObject.email = email
    }

    NavHost(navController = navController, startDestination = destination) {
        composable<Route.HomeScene> {
            navController.navigate(Route.P)
        }

    }
}




