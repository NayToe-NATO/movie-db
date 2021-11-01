package com.codigo.naytoe.moviedb.data.remote

import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.data.model.MovieList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieList>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

}