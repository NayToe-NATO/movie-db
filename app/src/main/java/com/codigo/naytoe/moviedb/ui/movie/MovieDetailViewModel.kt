package com.codigo.naytoe.moviedb.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.data.repository.MovieRepository
import com.codigo.naytoe.moviedb.network.Resource

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _movie = _id.switchMap { id ->
        repository.getMovie(id)
    }
    val movie: LiveData<Resource<Movie>> = _movie

    fun start(id: Int) {
        _id.value = id
    }

    suspend fun updateMovie(movie: Movie) = repository.updateMovie(movie)

}
