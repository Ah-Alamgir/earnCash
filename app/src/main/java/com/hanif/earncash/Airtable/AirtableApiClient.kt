import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AirtableApiClient {

    private val baseUrl = "https://api.airtable.com/v0/appCOC2vF3Cu4auYI"
    private val tableName = "Apps"
    private val apiKey = "pat6WUU8zXl4AIwVM.2e2888a14525b603038a0d7b973c612ae3d6ab755cc3f25b18bec1423110319e"

    private val client = OkHttpClient()
    fun storeStrings(string: String, fieldId: String, callback: Callback) {
        val data = mapOf("fields" to mapOf(fieldId to string))
        val json = Gson().toJson(data)

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$baseUrl/$tableName")
            .post(body)
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(callback)
    }


    fun getStrings(callback: Callback) {
        val request = Request.Builder()
            .url("$baseUrl/$tableName")
            .get()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(callback)
    }

    private fun convertListToJson(stringList: List<String>): String {
        return stringList.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"$it\"" }
    }
}
