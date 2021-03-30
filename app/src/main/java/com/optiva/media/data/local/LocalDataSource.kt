package com.optiva.media.data.local

import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.asFavoriteEntity
import com.optiva.media.domain.model.asMovieIdList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Cesar Conde
 */

@ExperimentalCoroutinesApi
class LocalDataSource @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend fun saveFavoriteMovie(movie: Movie) {
        return favoriteDao.saveFavoriteMovie(movie.asFavoriteEntity())
    }

    suspend fun deleteFavoriteMovie(movie: Movie) {
        return favoriteDao.deleteFavoriteMovie(movie.asFavoriteEntity())
    }

    suspend fun getAllFavoriteMoviesIds(): List<Long> {
        return favoriteDao.getAllFavoriteMovies().asMovieIdList()
    }

    suspend fun isMovieFavorite(movie: Movie): Boolean {
        return favoriteDao.getFavoriteById(movie.id) != null
    }
}