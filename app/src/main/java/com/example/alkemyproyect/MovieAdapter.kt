package com.example.alkemyproyect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(val movie:List<Movie>, var listener: OnClickListener): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movie[position]
        holder.bind(item)
        with(holder.binding.root){
            setOnLongClickListener { listener.onClick(item)
                true }
            setOnClickListener { listener.onClick2() }
        }
    }

    override fun getItemCount(): Int = movie.size

}