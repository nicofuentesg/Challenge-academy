package com.example.alkemyproyect.mainMovieModule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alkemyproyect.movieDetailsModule.Movie
import com.example.alkemyproyect.R
import com.example.alkemyproyect.mainMovieModule.OnClickListener

class MovieAdapter(val movie:List<Movie>, var listener: OnClickListener): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movie[position]
        holder.bind(item)
        with(holder.binding.root){

            setOnClickListener { listener.onClick(item) }
        }
    }

    override fun getItemCount(): Int = movie.size

}