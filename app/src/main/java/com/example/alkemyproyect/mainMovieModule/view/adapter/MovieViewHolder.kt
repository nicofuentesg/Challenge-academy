package com.example.alkemyproyect.mainMovieModule.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.alkemyproyect.mainMovieModule.view.utils.Constants
import com.example.alkemyproyect.mainMovieModule.model.Movie
import com.example.alkemyproyect.databinding.ItemListBinding

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemListBinding.bind(view)

    fun bind(movie: Movie){

        binding.titleMovie.text = movie.title
        Glide.with(binding.ivPhoto.context).load(Constants.BASE_URL_JPG + movie.posterPath )
            .centerCrop()
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPhoto)
    }
}