package com.aryanto.githubfinal.ui.fragment.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.data.remote.network.ApiService
import com.aryanto.githubfinal.utils.ClientState
import kotlinx.coroutines.launch

class FollowingVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _following = MutableLiveData<ClientState<List<Item>>>()
    val following: LiveData<ClientState<List<Item>>> = _following

    fun fetchFollowing(username: String) {
        viewModelScope.launch {
            _following.postValue(ClientState.Loading)

            try {
                val response = apiService.getFollowing(username)
                if (response.isSuccessful) {
                    val items = response.body() ?: throw IllegalAccessException("Body is null")
                    _following.postValue(ClientState.Success(items))
                }

            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                _following.postValue(ClientState.Error(errorMSG))
                Log.e("GAF-FVM following: ", errorMSG, e)
            }

        }
    }
}