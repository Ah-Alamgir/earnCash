package com.hanif.earncash.Repository

import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.DaO.UserDao
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun storeUserData(user: UserDao)
    fun storeFiverOrder(app: NonSubAppDao)
    suspend fun getUserDetails(): Flow<UserDao?>
    fun storeSubApp(app: AppDao): Boolean
    suspend fun getSubscribedApps(): Flow<List<AppDao>?>
    suspend fun getListedApps(): Flow<List<NonSubAppDao>?>

}
