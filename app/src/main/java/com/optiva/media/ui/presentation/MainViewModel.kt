package com.optiva.media.ui.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.optiva.media.core.Resource
import com.optiva.media.domain.model.Movie
import com.optiva.media.domain.model.VideoRecommendation
import com.optiva.media.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Cesar Conde
 */

class MainViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private lateinit var allMovies : List<Movie>

    private val _filteredMovies = MutableLiveData<List<Movie>>()
    val filteredMovies : LiveData<List<Movie>> get() = _filteredMovies

    private val _movieDetail = MutableLiveData<Resource<Movie?>>()
    val movieDetail : LiveData<Resource<Movie?>> get() = _movieDetail

    private val _recommendationList = MutableLiveData<Resource<List<VideoRecommendation>?>>()
    val recommendationList : LiveData<Resource<List<VideoRecommendation>?>> get() = _recommendationList

    fun searchMovies(str: String){
        if (str.isEmpty()){
            _filteredMovies.value = allMovies
        }else{
            _filteredMovies.value = allMovies.filter { it.name?.contains(str, ignoreCase = true) == true }
        }
    }

    var fetchMovieList = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                repository.getAllMovies().collect {
                    when (it) {
                        is Resource.Success -> {

                            var ids : List<Long>? = repository.getAllFavoriteMoviesIds()

                            if (!ids.isNullOrEmpty()){
                                for (movie in it.data) {
                                    if (ids.contains(movie.id)){
                                        movie.isFavorite = true
                                    }
                                }
                            }
                            allMovies = (it.data)
                            emit(it)
                        }
                        is Resource.Failure -> {
                            emit(it)
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
    }

    fun fetchMovieDetail(id : String){
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading
            withContext(Dispatchers.IO){
                try {
                    repository.getMovieDetail(id).collect {
                        _movieDetail.postValue(it)
                    }
                } catch (e: Exception) {
                    _movieDetail.postValue(Resource.Failure(e))
                }
            }
        }
    }

    fun saveOrDeleteFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (repository.isMovieFavorite(movie)) {
                    repository.deleteFavoriteMovie(movie)
                } else {
                    repository.saveFavoriteMovie(movie)
                }

                for(movieIter in allMovies){
                    if (movieIter.id == movie.id){
                        movieIter.isFavorite = !movieIter.isFavorite
                        break
                    }
                }
            }

            _filteredMovies.value = allMovies
        }
    }

    suspend fun isMovieFavorite(movie: Movie): Boolean =
            repository.isMovieFavorite(movie)

    fun fetchRecommendationList(id : String){
        viewModelScope.launch {
            _recommendationList.value = Resource.Loading
            withContext(Dispatchers.IO){
                try {
                    repository.getRecommendations(id).collect {
                        _recommendationList.postValue(it)
                    }
                } catch (e: Exception) {
                    _recommendationList.postValue(Resource.Failure(e))
                }
            }
        }
    }
}