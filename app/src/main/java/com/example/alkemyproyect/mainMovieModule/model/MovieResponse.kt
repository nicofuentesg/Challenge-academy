package com.example.alkemyproyect.mainMovieModule.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movie: List<Movie>
)