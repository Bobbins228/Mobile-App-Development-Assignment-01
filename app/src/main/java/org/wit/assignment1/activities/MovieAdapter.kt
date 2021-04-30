package org.wit.assignment1.activities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_movie.view.*
import kotlinx.android.synthetic.main.card_movie.view.*
import kotlinx.android.synthetic.main.card_movie.view.movieGenre
import kotlinx.android.synthetic.main.card_movie.view.movieTitle
import kotlinx.android.synthetic.main.card_movie.view.movieDirector
import kotlinx.android.synthetic.main.card_movie.view.movieReleaseDate
import org.wit.assignment1.R
import org.wit.assignment1.helpers.readImageFromPath
import org.wit.assignment1.models.movieListModel

interface MovieListener {
    fun onMovieClick(movie: movieListModel)
}

class MovieAdapter constructor(private var movies: List<movieListModel>,
                                   private val listener: MovieListener) : RecyclerView.Adapter<MovieAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val movie = movies[holder.adapterPosition]
        holder.bind(movie, listener)
    }

    override fun getItemCount(): Int = movies.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: movieListModel, listener : MovieListener) {
            itemView.movieTitle.text = movie.title
            itemView.movieGenre.text = movie.genre
            itemView.movieDirector.text = movie.director
            itemView.movieReleaseDate.text = movie.day.toString() + "/" + movie.month.toString() + "/" + movie.year.toString()
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, movie.image))
            itemView.setOnClickListener { listener.onMovieClick(movie) }
        }
    }
}