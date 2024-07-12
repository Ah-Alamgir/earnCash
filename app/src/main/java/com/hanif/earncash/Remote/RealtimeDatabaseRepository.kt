package com.hanif.earncash.Remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimeDatabaseRepository {

    private val database = FirebaseDatabase.getInstance()
    private val earningsRef = database.getReference("earnings")

    private fun sanitizeEmailForFirebaseKey(email: String): String {
        return email.replace('.', ',') // Replace periods with commas
    }

    fun storeEarningPoints(email: String, points: Int) {
        val sanitizedEmail = sanitizeEmailForFirebaseKey(email)
        earningsRef.child(sanitizedEmail).setValue(points)
    }

    fun getEarning(email: String): Flow<String> = callbackFlow {
        val sanitizedEmail = sanitizeEmailForFirebaseKey(email)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val points = snapshot.value.toString()
                trySend(points) // Emit the new points value
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        earningsRef.child(sanitizedEmail).addValueEventListener(listener)
        awaitClose { earningsRef.child(sanitizedEmail).removeEventListener(listener) }
    }
}