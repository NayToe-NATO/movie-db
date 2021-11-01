package com.codigo.naytoe.moviedb.data.remote

import com.codigo.naytoe.moviedb.util.AppConst
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
): BaseDataSource() {

    suspend fun getUpcomingMovies() = getResult { movieService.getUpcomingMovies(AppConst.API_KEY) }

    suspend fun getMovie(id: Int) = getResult { movieService.getMovie(id, AppConst.API_KEY) }

}