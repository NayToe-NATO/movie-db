package com.codigo.naytoe.moviedb.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id: Int,
    var overview: String,
    var popularity: Double,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    var title: String,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,
    var favourite: Boolean
)