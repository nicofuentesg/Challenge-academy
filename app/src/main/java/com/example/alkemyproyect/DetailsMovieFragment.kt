package com.example.alkemyproyect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.alkemyproyect.databinding.FragmentDetailsMovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(getString(R.string.key),0)
        Log.i("valor2", "$id")

        if (id != null && id != 0L ) {
            callDetails(id.toInt())
        }
        Log.i("valor2", "$id")
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DETAILS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun callDetails(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIResponse::class.java)
                .getMyMoviesDetails(id.toString() + Constants.API_KEY)
            val movieDetails: MovieDetailsResponse? = call.body()

            withContext(Dispatchers.Main) {
                binding.progresBar.visibility = View.GONE
                bind(movieDetails)

            }

        }


    }


    private fun bind(movieDetails: MovieDetailsResponse?) {
        binding.apply {

            cardView.visibility = View.VISIBLE
            tvDescriptionMovie.text = movieDetails?.overview
            release.text = movieDetails?.releaseDate
            duration.text = getString(R.string.duration) + runtime(movieDetails?.runtime)
            Glide.with(ivPhoto.context).load(Constants.BASE_URL_JPG + movieDetails?.posterPath)
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivPhoto)
            Log.i("poster", "Constants.BASE_URL_JPG + movieDetails?.posterPath")
            ratingBar.rating = movieDetails?.voteAverage!!.toFloat()
        }
    }

    private fun runtime(time:Int?):String{

        var formato:String  = "%02d:%02d";
        val horas:Long = TimeUnit.MINUTES.toHours(time!!.toLong())
        val minutos: Long = TimeUnit.MINUTES.toMinutes(time.toLong()) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(time.toLong()))


        return String.format(formato, horas, minutos)


    }

}
