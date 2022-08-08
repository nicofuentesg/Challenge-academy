package com.example.alkemyproyect.common

import com.example.alkemyproyect.mainMovieModule.MovieResponse
import com.example.alkemyproyect.movieDetailsModule.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIResponse {

    @GET
    suspend fun getMyMovies(@Url url: String): Response<MovieResponse>

    @GET
    suspend fun getMyMoviesDetails(@Url url: String): Response<MovieDetailsResponse>

}