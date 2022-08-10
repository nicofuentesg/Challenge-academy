package com.example.alkemyproyect.mainMovieModule.model.detailsMovieModel


import com.example.alkemyproyect.mainMovieModule.model.listMovieModel.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movie: List<Movie>
)