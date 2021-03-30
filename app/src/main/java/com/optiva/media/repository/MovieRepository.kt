package com.optiva.media.repository

import com.optiva.media.core.Resource
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.VideoRecommendation
import kotlinx.coroutines.flow.Flow

/**
 * Created by Cesar Conde
 */

interface MovieRepository {
    suspend fun getAllFavoriteMoviesIds(): List<Long>
    suspend fun saveFavoriteMovie(movie: Movie)
    suspend fun deleteFavoriteMovie(movie: Movie)
    suspend fun isMovieFavorite(movie: Movie): Boolean
    suspend fun getAllMovies(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetail(id : String): Flow<Resource<Movie?>>
    suspend fun getRecommendations(id : String): Flow<Resource<List<VideoRecommendation>>>
}