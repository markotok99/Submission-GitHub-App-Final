package com.aryanto.githubfinal.utils

import android.provider.ContactsContract.RawContacts.Data

sealed class ClientState<out T>() {
    data class Success<out T>(val data: T): ClientState<T>()
    data class Error(val error: String): ClientState<Nothing>()
    data object Loading : ClientState<Nothing>()
}