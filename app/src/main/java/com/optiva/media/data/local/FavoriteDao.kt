package com.optiva.media.data.local

import androidx.room.*
import com.optiva.media.domain.model.FavoritesEntity

/**
 * Created by Cesar Conde
 */

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoritesTable")
    suspend fun getAllFavoriteMovies(): List<FavoritesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteMovie(favorite: FavoritesEntity)

    @Delete
    suspend fun deleteFavoriteMovie(favorite: FavoritesEntity)

    @Query("SELECT * FROM favoritesTable WHERE movieId = :movieId")
    suspend fun getFavoriteById(movieId: Long): FavoritesEntity?
}