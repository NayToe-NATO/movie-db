package com.codigo.naytoe.moviedb.data.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    var page: Int,
    var results: List<Movie> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)