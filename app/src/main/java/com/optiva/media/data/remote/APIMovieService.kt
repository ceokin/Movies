package com.optiva.media.data.remote


import com.optiva.media.common.Constants
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.response.MoviesResponse
import com.optiva.media.domain.model.response.RecommendationsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Cesar Conde
 */
interface APIMovieService {

    @GET(Constants.GET_ALL_MOVIES_URL)
    suspend fun getAllMovies(): MoviesResponse?

    @GET(Constants.GET_MOVIE_DETAIL_URL)
    suspend fun getMovieDetail(@Query("external_id") id : String): Movie?

    @GET
    suspend fun getRecommendations(@Url url : String) : RecommendationsResponse?
}