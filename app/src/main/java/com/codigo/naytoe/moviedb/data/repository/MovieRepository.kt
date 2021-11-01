package com.codigo.naytoe.moviedb.data.repository

import com.codigo.naytoe.moviedb.data.local.MovieDao
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.data.remote.MovieRemoteDataSource
import com.codigo.naytoe.moviedb.util.performGetOperation
import com.codigo.naytoe.moviedb.util.performUpdateOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getUpcomingMovies() = performGetOperation(
        databaseQuery = { localDataSource.getUpcomingMovies() },
        networkCall = { remoteDataSource.getUpcomingMovies() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    fun getMovie(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(id) },
        networkCall = { remoteDataSource.getMovie(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

//    suspend fun getMovie(id: Int) = localDataSource.getMovie(id)

    suspend fun updateMovie(movie: Movie) = localDataSource.updateMovie(movie)

}