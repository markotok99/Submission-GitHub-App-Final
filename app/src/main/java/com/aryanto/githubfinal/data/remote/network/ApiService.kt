package com.aryanto.githubfinal.data.remote.network

import com.aryanto.githubfinal.data.model.Item
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getAllUser(): List<Item>

}