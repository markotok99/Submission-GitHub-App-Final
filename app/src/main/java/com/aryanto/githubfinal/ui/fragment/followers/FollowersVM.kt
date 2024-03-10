package com.aryanto.githubfinal.ui.fragment.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.data.remote.network.ApiService
import com.aryanto.githubfinal.utils.ClientState
import kotlinx.coroutines.launch

class FollowersVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _followers = MutableLiveData<ClientState<List<Item>>>()
    val followers: LiveData<ClientState<List<Item>>> = _followers

    fun fetchFollowers(username: String) {
        viewModelScope.launch {
            _followers.postValue(ClientState.Loading)

            try {
                val response = apiService.getFollowers(username)
                if (response.isSuccessful) {
                    val items = response.body() ?: throw IllegalAccessException("Body is null")
                    _followers.postValue(ClientState.Success(items))
                }

            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _followers.postValue(ClientState.Error(errorMSG))
                Log.e("GAF-FVM followers: ", errorMSG, e)
            }

        }
    }
}