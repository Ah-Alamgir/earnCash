package com.hanif.earncash.Airtable

import android.util.Log
import com.hanif.earncash.DaO.SubscribedDao
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class SupabaseClient{

    @Serializable
    data class Country(
        val date: String,
        val name: String,
    )

    val supabase = createSupabaseClient(
        supabaseUrl = "https://mxvgezcghaugxzpufeiq.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im14dmdlemNnaGF1Z3h6cHVmZWlxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjA3ODc5MjAsImV4cCI6MjAzNjM2MzkyMH0.pQVY-zvAcVKcPD0rJ_8TprMW4cqH2tZ1PczuZ03SOMc"
    ) {
        install(Postgrest)
    }
    val testerDb = supabase.from("Testers") // Table to track user daily used app
    val subAppDB = supabase.from("SubApps") // Table to store user subscribed app













    suspend fun getSubApp(email: String): List<Country> {
        return subAppDB.select(columns = Columns.list(email)).decodeList<Country>()
    }

    fun storeSub(email: String, appDao: SubscribedDao){

    }

    suspend fun storeSubApp(email: String, appDao: SubscribedDao){ //storing the app details which the user subscribed from the NonSubscribed scene
        try {
            val appInfo = Json.encodeToString(appDao)
            Log.d("luckey", appInfo)

            val dynamicData: Map<String, String> = mapOf("ahalamgir.16" to  appInfo)
            Log.d("luckey", "we are here")
            subAppDB.insert(dynamicData)
        }catch (e:Exception){
            Log.d("luckey", "storeSub: ${e.message}")

        }

    }

    suspend fun getCheckedApp(email: String): List<Country> {
        return testerDb.select(columns = Columns.list(email)).decodeList<Country>()
    }


}