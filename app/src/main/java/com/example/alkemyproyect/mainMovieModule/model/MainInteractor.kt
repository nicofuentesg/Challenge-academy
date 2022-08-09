package com.example.alkemyproyect.mainMovieModule.model

import com.example.alkemyproyect.mainMovieModule.model.RetrofitHelper.getRetrofit
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainInteractor {


    fun getMovieApi(callback: (List<Movie>)-> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit()
                .create(APIResponse::class.java)
                .getMyMovies(Constants.API_PATH + Constants.API_POPULAR)

            val movie: MovieResponse? = call.body()


            withContext(Dispatchers.Main) {
                val movies = movie?.movie ?: emptyList()

                if (call.isSuccessful){
                    callback(movies)
                }else{
                   /* MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.title)
                        .setView(layoutInflater.inflate(R.layout.dialog_alert, null)).setCancelable(false)
                        .setPositiveButton("reintentar...") { _, i ->
                            onDestroy()
                        }
                        .show()
                    //show error */
                }
            }

        }

    }


}