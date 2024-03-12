package com.aryanto.githubfinal.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ThemePref private constructor(private val pref: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return pref.data.map { pref ->
            pref[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        pref.edit { pref ->
            pref[THEME_KEY] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePref? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemePref {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePref(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}