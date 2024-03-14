package com.aryanto.githubfinal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDAO

    companion object{
        private var INSTANCE:FavoriteDB? = null

        fun getDB(context: Context): FavoriteDB{
            if (INSTANCE == null){
                synchronized(FavoriteDB::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDB::class.java, "favorite_DB")
                        .build()
                }
            }
            return INSTANCE as FavoriteDB
        }
    }
}