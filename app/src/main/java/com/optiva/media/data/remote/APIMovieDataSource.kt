package com.optiva.media.data.remote

import com.optiva.media.common.Constants
import com.optiva.media.core.Resource
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.VideoRecommendation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Created by Cesar Conde
 */


@ExperimentalCoroutinesApi
class APIMovieDataSource @Inject constructor(
    private val webService: APIMovieService
) {
    suspend fun getAllMovies(): Flow<Resource<List<Movie>>> =
        callbackFlow {

            offer(
                Resource.Success(
                    webService.getAllMovies()?.movies ?: listOf()
                )
            )
            awaitClose { close() }
        }

    suspend fun getMovieDetail(id : String): Flow<Resource<Movie?>> =
        callbackFlow {

            offer(
                Resource.Success(
                        webService.getMovieDetail(id)
                )
            )
            awaitClose { close() }
        }

    suspend fun getRecommendations(id : String): Flow<Resource<List<VideoRecommendation>>> =
            callbackFlow {

                offer(
                        Resource.Success(
                                webService.getRecommendations(Constants.GET_MOVIE_RECOMMENDATIONS_URL + "?type=all&subscription=false&filter_viewed_content=true&max_results=10&blend=ar_od_blend_2424video&max_pr_level=8&quality=SD,HD&services=2424VIDEO&params=external_content_id:$id")?.videoRecommendation ?: listOf()
                        )
                )
                awaitClose { close() }
            }
}