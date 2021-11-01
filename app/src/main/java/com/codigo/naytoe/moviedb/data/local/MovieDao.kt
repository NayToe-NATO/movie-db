package com.codigo.naytoe.moviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codigo.naytoe.moviedb.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getUpcomingMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id LIKE :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun updateMovie(vararg movie: Movie)

}