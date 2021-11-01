package com.codigo.naytoe.moviedb.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.text.FieldPosition

class MovieViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movies = repository.getUpcomingMovies()

    suspend fun updateMovie(movie: Movie) = repository.updateMovie(movie)

}