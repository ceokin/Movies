package com.optiva.media.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.optiva.media.domain.model.FavoritesEntity

/**
 * Created by Cesar Conde
 */
@Database(entities = [FavoritesEntity::class],version = 1, exportSchema = false)
    abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}