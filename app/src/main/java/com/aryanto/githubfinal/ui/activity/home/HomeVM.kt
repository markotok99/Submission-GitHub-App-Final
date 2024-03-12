package com.aryanto.githubfinal.ui.activity.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.data.remote.network.ApiService
import com.aryanto.githubfinal.utils.ClientState
import com.aryanto.githubfinal.utils.ThemePref
import kotlinx.coroutines.launch

class HomeVM(
    private val apiService: ApiService,
    private val themePref: ThemePref
) : ViewModel() {

    private val _users = MutableLiveData<ClientState<List<Item>>>()
    val users: LiveData<ClientState<List<Item>>> = _users

    val isDarkMode: LiveData<Boolean> = themePref.getThemeSetting().asLiveData()

    fun getAllUsers() {
        viewModelScope.launch {
            _users.postValue(ClientState.Loading)

            try {
                val response = apiService.getAllUser()

                if (response.isEmpty()) {
                    throw IllegalAccessException()
                } else {
                    _users.postValue(ClientState.Success(response))
                }
            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                Log.e("GAF-HVM", errorMSG, e)
                _users.postValue(ClientState.Error(errorMSG))
            }

        }
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            _users.postValue(ClientState.Loading)

            try {
                val response = apiService.searchUser(query)

                if (response.isSuccessful) {
                    _users.postValue(
                        ClientState.Success(
                            response.body()?.items ?: throw IllegalAccessException("Body is null")
                        )
                    )
                } else {
                    throw IllegalAccessException("Data response fail")
                }
            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                Log.e("GAF-HVM", errorMSG, e)
                _users.postValue(ClientState.Error(errorMSG))
            }

        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            themePref.saveThemeSetting(isDarkMode)
        }
    }

}