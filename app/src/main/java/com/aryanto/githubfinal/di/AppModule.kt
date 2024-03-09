package com.aryanto.githubfinal.di

import com.aryanto.githubfinal.data.remote.network.ApiClient
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
}