package com.example.alkemyproyect.mainMovieModule.viewModels.detailsMovieViewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkemyproyect.mainMovieModule.model.APIResponse
import com.example.alkemyproyect.mainMovieModule.model.MovieDetailsResponse
import com.example.alkemyproyect.mainMovieModule.model.MovieResponse
import com.example.alkemyproyect.mainMovieModule.model.RetrofitHelper
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class DetailsViewModel(): ViewModel() {

    var movieDetail = MutableLiveData<MovieDetailsResponse>()
    var error =  MutableLiveData<String>()


    fun getDetails(id: Int) {

        viewModelScope.launch {

            try {


                val call = RetrofitHelper.getRetrofitDetails().create(APIResponse::class.java)
                    .getMyMoviesDetails(id.toString() + Constants.API_KEY)
                val movieDetails: MovieDetailsResponse = call.body()!!


                if (call.isSuccessful) {
                    movieDetail.postValue(movieDetails)
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is TimeoutCancellationException -> {
                        error.postValue("se acabo el tiempo")

                    }
                    is IOException -> {

                        error.postValue("Error de red")
                    }
                    is HttpException -> {
                        error.postValue("Errores de servidor")
                    }
                    else -> {
                        error.postValue(":(")

                    }
                }
            }


        }


    }
}