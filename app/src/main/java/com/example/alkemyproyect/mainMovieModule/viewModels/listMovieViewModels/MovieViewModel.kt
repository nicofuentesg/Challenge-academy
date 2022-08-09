package com.example.alkemyproyect.mainMovieModule.viewModels.listMovieViewModels

import androidx.lifecycle.*
import com.example.alkemyproyect.mainMovieModule.model.RetrofitHelper
import com.example.alkemyproyect.mainMovieModule.model.APIResponse
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import com.example.alkemyproyect.mainMovieModule.model.MovieResponse
import com.example.alkemyproyect.mainMovieModule.model.Movie
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MovieViewModel: ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    var error =  MutableLiveData<String>()
    var isloading = MutableLiveData<Boolean>()
    //state de carga

    fun getMovies() {


        viewModelScope.launch {
            isloading.postValue(true)
            //var issuccess: String? = null
            try {
                // throws TimeoutCancellationException
               // withTimeout(100) {
                    //NetworkResponse.Success(apiCall.invoke())
                      var call = RetrofitHelper.getRetrofit()
                        .create(APIResponse::class.java)
                        .getMyMovies(Constants.API_PATH + Constants.API_POPULAR)


                //}

                val movie: MovieResponse? = call.body()
                val movies = movie?.movie ?: emptyList()
                if (call.isSuccessful) {
                    movieList.postValue(movies)
                } else {

                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is TimeoutCancellationException -> {
                        val code = 408 // timeout error code
                        error.postValue("se acabo el tiempo")
                        //NetworkResponse.GenericError(code, NETWORK_ERROR_TIMEOUT)
                    }
                    is IOException -> {

                        error.postValue("Error de red")
                        //NetworkResponse.NetworkError
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        error.postValue("ERrores de servidor")
                        // NetworkResponse.GenericError(
                        /*    code,
                                    errorResponse*/
                        //   )
                    }
                    else -> {
                        //error = ":("
                        // NetworkResponse.GenericError(
                        //  null,
                        //  NETWORK_ERROR_UNKNOWN
                        // )
                    }
                }
            }
            isloading.postValue(false)

        }
    }

    /* MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.title)
                                .setView(layoutInflater.inflate(R.layout.dialog_alert, null)).setCancelable(false)
                                .setPositiveButton("reintentar...") { _, i ->
                                    onDestroy()
                                }
                                .show()
                            //show error */
}

