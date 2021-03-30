package com.optiva.media.repository

import com.optiva.media.core.Resource
import com.optiva.media.data.local.LocalDataSource
import com.optiva.media.data.remote.APIMovieDataSource
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.VideoRecommendation
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class MovieDataRepository @Inject constructor(
    private val mAPIMovieDataSource: APIMovieDataSource,
    private val mLocalDataSource: LocalDataSource
) : MovieRepository {

    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> =
        callbackFlow {

            mAPIMovieDataSource.getAllMovies().collect {
                offer(it)
            }
            awaitClose { cancel() }
        }

    override suspend fun getMovieDetail(id : String): Flow<Resource<Movie?>> =
        callbackFlow {

            mAPIMovieDataSource.getMovieDetail(id).collect {
                offer(it)
            }
            awaitClose { cancel() }
        }

    override suspend fun getRecommendations(id: String): Flow<Resource<List<VideoRecommendation>>> =
            callbackFlow {

                mAPIMovieDataSource.getRecommendations(id).collect {
                    offer(it)
                }
                awaitClose { cancel() }
            }

    override suspend fun getAllFavoriteMoviesIds(): List<Long> {
        return mLocalDataSource.getAllFavoriteMoviesIds()
    }

    override suspend fun saveFavoriteMovie(movie: Movie) {
        mLocalDataSource.saveFavoriteMovie(movie)
    }

    override suspend fun isMovieFavorite(movie: Movie): Boolean =
            mLocalDataSource.isMovieFavorite(movie)

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        mLocalDataSource.deleteFavoriteMovie(movie)
    }

}