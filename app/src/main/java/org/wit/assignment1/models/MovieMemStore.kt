package org.wit.assignment1.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MovieMemStore : MovieStore, AnkoLogger {

    val movies = ArrayList<movieListModel>()

    override fun findAll(): List<movieListModel> {
        return movies
    }

    override fun create(movie: movieListModel) {
        movies.add(movie)
    }

    override fun update(movie: movieListModel) {
        var foundMovie: movieListModel? = movies.find { m -> m.id == movie.id }
        if (foundMovie != null) {
            foundMovie.title = movie.title
            foundMovie.genre = movie.genre
            foundMovie.image = movie.image
            foundMovie.lat = movie.lat
            foundMovie.lng = movie.lng
            foundMovie.zoom = movie.zoom
            logAll()
        }
    }

    override fun delete(movie: movieListModel) {
        movies.remove(movie)
    }

    fun logAll() {
        movies.forEach{ info("${it}") }
    }
}