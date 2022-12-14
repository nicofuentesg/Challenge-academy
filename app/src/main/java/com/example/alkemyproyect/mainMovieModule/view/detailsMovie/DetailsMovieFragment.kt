package com.example.alkemyproyect.mainMovieModule.view.detailsMovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import com.example.alkemyproyect.R
import com.example.alkemyproyect.databinding.FragmentDetailsMovieBinding
import com.example.alkemyproyect.mainMovieModule.model.detailsMovieModel.MovieDetailsResponse
import com.example.alkemyproyect.mainMovieModule.viewModels.detailsMovieViewModels.DetailsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.concurrent.TimeUnit


class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(getString(R.string.key), 0)

        if (id != null && id != 0L) {
            setupViewModel(id.toInt())
        }

    }


    private fun setupViewModel(id: Int) {

        viewModel.getDetails(id)

        viewModel.movieDetail.observe(viewLifecycleOwner, Observer { movieDetails ->
            bind(movieDetails)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->

            MaterialAlertDialogBuilder(requireContext()).setTitle(error)
                .setView(layoutInflater.inflate(R.layout.dialog_alert, null))
                .setCancelable(false).setPositiveButton("Salir... ", { _, i ->
                }).show()

        })

    }


    @SuppressLint("SetTextI18n")
    private fun bind(movieDetails: MovieDetailsResponse?) {
        binding.apply {

            tvDescriptionMovie.text = movieDetails?.overview
            release.text = movieDetails?.releaseDate
            duration.text = getString(R.string.duration) + runtime(movieDetails?.runtime)

            Glide.with(ivPhoto.context).load(Constants.BASE_URL_JPG + movieDetails?.posterPath)
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivPhoto)

            ratingBar.rating = movieDetails?.voteAverage!!.toFloat()
            cardView.visibility = View.VISIBLE
            binding.btnPlayMovie.setOnClickListener { messageError() }
        }
    }


    private fun runtime(time: Int?): String {

        val formato: String = "%02d:%02d";
        val horas: Long = TimeUnit.MINUTES.toHours(time!!.toLong())
        val minutos: Long = TimeUnit.MINUTES.toMinutes(time.toLong()) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MINUTES.toHours(time.toLong())
        )


        return String.format(formato, horas, minutos)


    }

    private fun messageError(){
        Toast.makeText(context,getString(R.string.message_error),Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
