package com.hanif.earncash.Remote

import android.content.Context

class sharePref {
    fun storeEmail(email: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply() // Use apply() for asynchronous saving
    }

     fun getEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", "no").toString() // Return null if email not found
    }

    // Define an Entity for your strings



}