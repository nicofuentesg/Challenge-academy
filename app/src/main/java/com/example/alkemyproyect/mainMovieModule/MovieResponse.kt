package com.example.alkemyproyect.mainMovieModule


import com.example.alkemyproyect.movieDetailsModule.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movie: List<Movie>
)