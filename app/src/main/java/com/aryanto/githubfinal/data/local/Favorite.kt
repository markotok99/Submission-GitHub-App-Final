package com.aryanto.githubfinal.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String?

): Parcelable
