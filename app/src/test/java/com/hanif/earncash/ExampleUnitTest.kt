package com.hanif.earncash

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val baseUrl = "https://api.airtable.com/v0/appCOC2vF3Cu4auYI/Apps"
        val apiKey = "pat6WUU8zXl4AIwVM.2e2888a14525b603038a0d7b973c612ae3d6ab755cc3f25b18bec1423110319e"
        val client = OkHttpClient()


        val request = Request.Builder()
            .url("$baseUrl/fields%5B%5D=Date?maxRecords=3&view=Grid%2view")
            .get()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.toString())
            }
        })

    }
}