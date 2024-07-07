package com.hanif.earncash.Utils

import android.content.Context
import com.hanif.earncash.Remote.FirestoreUserDao
import com.hanif.earncash.Remote.RealtimeDatabaseRepository

class CallFunctions(context: Context){
    companion object{
        val fireObject = FirestoreUserDao()
        var realTimeObject = RealtimeDatabaseRepository()
    }
}