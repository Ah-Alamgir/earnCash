package com.hanif.earncash.Airtable

import AirtableApiClient
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel:ViewModel() {
    private val airtableApiClient = AirtableApiClient()
    private val _apireSultsubscribed = MutableStateFlow(Result.success(""))
    val apiResultSubscribed: StateFlow<Result<String>> = _apireSultsubscribed

//    fun storNonSub(url:String, datMap: Map<String, Map<String, String>>) {
//        viewModelScope.launch {
//            airtableApiClient.createNewData(url, datMap).collect{result->
//                _apireSultsubscribed.value = result
//
//            }
//        }
//    }





}