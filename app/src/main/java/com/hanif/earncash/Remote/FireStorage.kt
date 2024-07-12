package com.hanif.earncash.Remote

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.DaO.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class FirestoreUserDao {
    var point: Int = 0
    private val db = Firebase.firestore
    private val userDetailsCollection = db.collection("user_details")
    private val userEarningCollection = db.collection("user_earning")
    private val subscribedAppsCollection = db.collection("SubsCribedApps")
    private val appTester = db.collection("TesterGamils")
    private val testDates = db.collection("Tested_Dates")
    var email = "a"

    suspend fun storeUserData(user: UserDao) {
        userDetailsCollection.document(user.email).set(user).await()
    }

    fun storeFiverOrder(app: NonSubAppDao) {
        val data = db.collection("OrderFromFiver").add(app).isSuccessful
        Log.d("luckey", data.toString())

    }









    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }




    suspend fun getUserDetails(): Flow<UserDao?> = flow {
        try {
            val documentSnapshot = userDetailsCollection.document(email).get().await()
            if (documentSnapshot.exists()) {
                val dao = documentSnapshot.toObject(UserDao::class.java)
                emit(dao)
            }
        } catch (e: Exception) {
            println("Error getting user details: ${e.message}")
        }
    }




    fun storeSubApp(app: AppDao): Boolean {
        var isSuccess = false
        subscribedAppsCollection.document(email).collection("apps").add(app).addOnCompleteListener {
            if (it.isSuccessful) {
                isSuccess = true
            }

        }
        return isSuccess

    }












    suspend fun getSubscribedApps(): Flow<List<AppDao>?> = flow {
        val userAppsCollection = subscribedAppsCollection.document(email).collection("apps")
        val querySnapshot = userAppsCollection.get().await()
        val appList = querySnapshot.toObjects(AppDao::class.java)
        emit(appList)
    }


    suspend fun getListedApps(): Flow<List<NonSubAppDao>?> = flow {
        val userAppsCollection = db.collection("OrderFromFiver")
        val querySnapshot = userAppsCollection.get().await()
        val appList = querySnapshot.toObjects(NonSubAppDao::class.java)
        emit(appList)
    }


}