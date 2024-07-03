package com.hanif.earncash.Remote

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Subscribed_Packages(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val value: String
)

// Create a DAO (Data Access Object) to interact with the database
@Dao
interface MyStringDao {
    @Insertsuspend
    fun insert(myString: Subscribed_Packages)

    @Query("SELECT * FROM Subscribed_Packages")
    fun getAll(): Flow<List<Subscribed_Packages>>
}


annotation class Insertsuspend
