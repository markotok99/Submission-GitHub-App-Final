package com.aryanto.githubfinal.data.remote.network

import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.data.model.ItemDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getAllUser(): List<Item>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): Response<ItemDetail>

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): Response<List<Item>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): Response<List<Item>>

}