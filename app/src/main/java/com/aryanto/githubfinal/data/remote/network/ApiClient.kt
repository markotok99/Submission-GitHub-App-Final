package com.aryanto.githubfinal.data.remote.network

import com.aryanto.githubfinal.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = BuildConfig.BASE_URL
    private const val API_TOKEN = BuildConfig.API_TOKEN

    fun getApiService(): ApiService {
        val interceptorLog = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val interceptorAuth = Interceptor { chain ->
            val newReq = chain.request()
                .newBuilder()
                .addHeader("Authorization", API_TOKEN)
                .build()
            chain.proceed(newReq)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptorLog)
            .addInterceptor(interceptorAuth)
            .build()

        val retrofitInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitInstance.create(ApiService::class.java)
    }

//        fun setToken() {
//        API_TOKEN = "token $API_TOKEN"
//        Log.d("ApiClient", "Token set: $API_TOKEN")
//    }

}