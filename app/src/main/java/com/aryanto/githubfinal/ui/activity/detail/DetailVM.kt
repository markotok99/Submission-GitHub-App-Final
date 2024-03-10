package com.aryanto.githubfinal.ui.activity.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.githubfinal.data.model.ItemDetail
import com.aryanto.githubfinal.data.remote.network.ApiService
import com.aryanto.githubfinal.utils.ClientState
import kotlinx.coroutines.launch

class DetailVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _detailUser = MutableLiveData<ClientState<List<ItemDetail>>>()
    val detailUser: LiveData<ClientState<List<ItemDetail>>> = _detailUser

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            _detailUser.postValue(ClientState.Loading)
            try {
                val response = apiService.getDetailUser(username)

                if (response.isSuccessful) {
                    response.body()?.let { item ->
                        _detailUser.postValue(ClientState.Success(listOf(item)))
                    } ?: throw IllegalAccessException("Body is null")
                } else {
                    throw IllegalAccessException("${response.body()?.toString()}")
                }
                Log.d("GH-DVM", "API Response: ${response.body().toString()}")
            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _detailUser.postValue(ClientState.Error(errorMSG))
                Log.e("GH-DVM Get Detail User: ", errorMSG, e)
            }
        }
    }
}