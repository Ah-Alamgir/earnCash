package com.hanif.earncash.Repository

import com.hanif.earncash.DaO.AppDao
import com.hanif.earncash.DaO.NonSubAppDao
import com.hanif.earncash.DaO.UserDao
import com.hanif.earncash.Remote.FirestoreUserDao
import kotlinx.coroutines.flow.Flow

class FirestoreUserRepository : UserRepository {

    private val dao = FirestoreUserDao()

    override suspend fun storeUserData(user: UserDao) {
        dao.storeUserData(user)
    }

    override fun storeFiverOrder(app: NonSubAppDao) {
        dao.storeFiverOrder(app)
    }

    override suspend fun addTestedDates(appName: ArrayList<String>) {
        dao.addTestedDates(appName)
    }

    override suspend fun getUserDetails(): Flow<UserDao?> {
        return dao.getUserDetails()
    }

    override fun storeSubApp(app: AppDao): Boolean {
        return dao.storeSubApp(app)
    }

    override suspend fun getSubscribedApps(): Flow<List<AppDao>?> {
        return dao.getSubscribedApps()
    }

    override suspend fun getListedApps(): Flow<List<NonSubAppDao>?> {
        return dao.getListedApps()
    }

    override fun storEanring(amount: Int) {
        dao.storEanring(amount)
    }

    override suspend fun getEanring(): Int {
        return dao.getEanring()
    }
}
