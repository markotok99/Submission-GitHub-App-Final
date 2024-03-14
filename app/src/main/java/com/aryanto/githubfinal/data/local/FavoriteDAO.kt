package com.aryanto.githubfinal.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM Favorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Query("select * from Favorite where login = :login")
    suspend fun isFavorite(login: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

}