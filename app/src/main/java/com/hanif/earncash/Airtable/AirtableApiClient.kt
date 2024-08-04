
import android.util.Log
import com.google.gson.Gson
import com.hanif.earncash.Airtable.DataClass.CheckAppList
import com.hanif.earncash.Airtable.DataClass.CheckedApp
import com.hanif.earncash.DaO.ApiResponse
import com.hanif.earncash.DaO.NonSubAppDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class AirtableApiClient {


    private val baseUrl = "https://api.airtable.com/v0/appCOC2vF3Cu4auYI/tblNhIOx9aYKCaxlc"
    private val apiKey =
        "pat6WUU8zXl4AIwVM.2e2888a14525b603038a0d7b973c612ae3d6ab755cc3f25b18bec1423110319e"
    private val client = OkHttpClient()


    fun convertToText(){
        val facebookApp = NonSubAppDao(
            "Facebook",
            "https://www.facebook.com",
            "https://example.com/facebook_icon.png",
            "com.facebook.katana",
            2,
            "recjrWON9pGG90vtH"
        )
        val json = Gson().toJson(facebookApp)
        println(json)
    }

    fun storeStrings(apName: String, fieldId: String, callback: Callback) {
        val data = mapOf("fields" to mapOf(fieldId to apName))
        val json = Gson().toJson(data)

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(baseUrl)
            .post(body)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(callback)
    }


    data class ApiError(override val message: String): Throwable(message)

    fun getCheckedApp(filter: String): Flow<Result<CheckedApp>> = flow {
        val request = Request.Builder()
            .url(baseUrl + filter)
            .get()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

         try {
             val response = client.newCall(request).execute() // Execute the request synchronously
             if (response.isSuccessful) {
                 response.body?.let {
                     val jsonResponse = it.string()
                     val gson = Gson()
                     val airtableData = gson.fromJson(jsonResponse, CheckAppList::class.java)
                     airtableData.records.forEach {record->
                         record.fields.forEach{fields->
                             if (fields.key == "Date" && fields.value== "2024-07-11"){
                                emit(Result.success(record))
                             }else{
                                 emit(Result.failure(ApiError("আজ এপ টেস্ট করেন নি  ")))
                             }
                         }
                     }
                 } ?: emit(Result.failure(ApiError("Empty response body."))) // Emit null if body is empty
             } else {
                 emit(Result.failure(ApiError("কিছু পাওয়া যায়নি"))) // Emit null on unsuccessful response
             }
         } catch (e: IOException) {
             emit(Result.failure(ApiError("আপনার নেটওয়ার্ক কানেকশন  চেক করুন ")))
         }
    }

















    /// for the nonsubscribed scene

    fun getListedApps():Flow<Result<List<NonSubAppDao>>> = flow {
        val request = Request.Builder()
            .url("https://api.airtable.com/v0/appCOC2vF3Cu4auYI/Applist")
            .get()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        try {
            val response = client.newCall(request).execute() // Execute the request synchronously
            if (response.isSuccessful) {
                response.body?.let {
                    val jsonResponse = it.string()
                    val apiResponse = Gson().fromJson(jsonResponse, ApiResponse::class.java)
                    val nonSubAppList = mutableListOf<NonSubAppDao>()

                    for (record in apiResponse.records) {
                        val appJson = record.fields.OrderdApps
                        val app = Gson().fromJson(appJson, NonSubAppDao::class.java)
                        nonSubAppList.add(app)
                    }

                    println(nonSubAppList)
                    emit(Result.success(nonSubAppList))


                }  ?: emit(Result.failure(ApiError("Empty response body."))) // Emit null if body is empty
            } else {
                emit(Result.failure(ApiError("কিছু পাওয়া যায়নি"))) // Emit null on unsuccessful response
            }
        } catch (e: IOException) {
            emit(Result.failure(ApiError("আপনার নেটওয়ার্ক কানেকশন  চেক করুন ")))
        }
    }



























    private fun convertListToJson(stringList: List<String>): String {
        return stringList.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"$it\"" }
    }

    fun createField() {
        val json = """
        {
            "description": "Whether I have visited this apartment yet.",
            "name": "IconUrl",
            "options": {
                "color": "greenBright",
                "icon": "check"
            },
            "type": "checkbox"
        }
    """.trimIndent()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$baseUrl/bases/appCOC2vF3Cu4auYI/tables/tblNhIOx9aYKCaxlc/fields")
            .post(body)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("luckey", response.code.toString())
            }

        })

    }



    fun updateData(varargs: SafeVarargs) {
        val json = JSONObject()
        json.put("fields", JSONObject().put("c", "newValue"))

        val body = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())


        val request = Request.Builder()
            .url("$baseUrl/recjrWON9pGG90vtH")
            .patch(body)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Record updated successfully")
                } else {
                    println(response.code)
                }
            }
        })
    }


}
