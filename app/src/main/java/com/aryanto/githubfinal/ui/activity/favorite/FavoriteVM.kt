package com.aryanto.githubfinal.ui.activity.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.githubfinal.data.local.Favorite
import com.aryanto.githubfinal.data.local.FavoriteDAO
import com.aryanto.githubfinal.utils.ClientState
import kotlinx.coroutines.launch

class FavoriteVM(
    private val favoriteDAO: FavoriteDAO
) : ViewModel() {

    private val _favorites = MutableLiveData<ClientState<List<Favorite>>>()
    val favorites: LiveData<ClientState<List<Favorite>>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.postValue(ClientState.Loading)
            try {
                val favoriteList = favoriteDAO.getAllFavorite()
                _favorites.postValue(ClientState.Success(favoriteList))
                Log.d("GAF-FVM", "Loaded ${favoriteList.size} favorites")

            } catch (e: Exception) {
                val errorMSG = "${e.message}"
                Log.e("GAF-FVM", "Error loading favorites", e)
                _favorites.postValue(ClientState.Error(errorMSG))
            }
        }
    }

}