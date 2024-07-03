package com.hanif.earncash.Remote

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.hanif.earncash.DaO.NonSubAppDao
import kotlinx.coroutines.tasks.await

object FetchApp: ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val appDaoRef = database.getReference("appList") // Adjust the reference path as needed

    suspend fun storeAppDao(appDao: NonSubAppDao) {
        appDaoRef.push().setValue(appDao).await()
    }

    suspend fun getAppDaoList(): List<NonSubAppDao> {
        val appDaoList = mutableListOf<NonSubAppDao>()
        val snapshot = appDaoRef.get().await()
        for (child in snapshot.children) {
            val appDao = child.getValue(NonSubAppDao::class.java)
            appDao?.let { appDaoList.add(it) }
        }
        return appDaoList
    }
}