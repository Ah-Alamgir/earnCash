package com.hanif.earncash.Utils

import com.hanif.earncash.Remote.FirestoreUserDao
import com.hanif.earncash.Remote.RealtimeDatabaseRepository

class CallFunctions {
    companion object{
        val fireObject = FirestoreUserDao()
        var realTimeObject = RealtimeDatabaseRepository()
    }
}