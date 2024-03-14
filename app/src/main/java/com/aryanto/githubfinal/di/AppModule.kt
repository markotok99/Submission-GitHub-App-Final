package com.aryanto.githubfinal.di

import com.aryanto.githubfinal.data.local.FavoriteDB
import com.aryanto.githubfinal.data.remote.network.ApiClient
import com.aryanto.githubfinal.utils.ThemePref
import com.aryanto.githubfinal.utils.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
    single { ThemePref.getInstance(androidContext().dataStore) }
    single { FavoriteDB.getDB(androidContext()) }
    single { get<FavoriteDB>().favoriteDao() }
}