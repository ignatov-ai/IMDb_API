package com.example.imdb_api

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class FilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val image = itemView.findViewById<ImageView>(R.id.imageUrl)
    private val title = itemView.findViewById<TextView>(R.id.titleText)
    private val description = itemView.findViewById<TextView>(R.id.descriptionText)

    fun bind(film: Film){

        Glide.with(itemView)
            .load(film.image)
            .placeholder(R.drawable.no_film_icon)
            .centerCrop()
            .fitCenter()
            .transform(RoundedCorners(5))
            .into(image)

        title.text = film.title
        description.text = film.description
    }
}