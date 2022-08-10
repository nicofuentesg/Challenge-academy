package com.example.alkemyproyect.mainMovieModule.viewModels.listMovieViewModels

import androidx.lifecycle.*
import com.example.alkemyproyect.mainMovieModule.model.network.RetrofitHelper
import com.example.alkemyproyect.mainMovieModule.model.network.APIResponse
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import com.example.alkemyproyect.mainMovieModule.model.detailsMovieModel.MovieResponse
import com.example.alkemyproyect.mainMovieModule.model.listMovieModel.Movie
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class MovieViewModel: ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    var error =  MutableLiveData<String>()
    var isloading = MutableLiveData<Boolean>()
    //state de carga

    fun getMovies() {

        viewModelScope.launch {
            isloading.postValue(true)

            try {


                      var call = RetrofitHelper.getRetrofit()
                        .create(APIResponse::class.java)
                        .getMyMovies(Constants.API_PATH + Constants.API_POPULAR)




                val movie: MovieResponse? = call.body()
                val movies = movie?.movie ?: emptyList()
                if (call.isSuccessful) {
                    movieList.postValue(movies)

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
            isloading.postValue(false)

        }
    }


}

