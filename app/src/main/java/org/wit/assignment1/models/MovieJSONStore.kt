package org.wit.assignment1.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.assignment1.helpers.exists
import org.wit.assignment1.helpers.read
import org.wit.assignment1.helpers.write
import java.util.*

val JSON_FILE = "movies.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<movieListModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MovieJSONStore : MovieStore, AnkoLogger {

    val context: Context
    var movies = mutableListOf<movieListModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<movieListModel> {
        return movies
    }

    override fun create(movie: movieListModel) {
        movie.id = generateRandomId()
        movies.add(movie)
        serialize()
    }


    override fun update(movie: movieListModel) {
        val moviesList = findAll() as ArrayList<movieListModel>
        var foundMovie: movieListModel? = moviesList.find { m -> m.id == movie.id}

        if (foundMovie != null) {
            foundMovie.title = movie.title
            foundMovie.genre = movie.genre
            foundMovie.image = movie.image
            foundMovie.lat = movie.lat
            foundMovie.zoom = movie.zoom
        }
        serialize()
    }

    override fun delete(movie: movieListModel) {
        movies.remove(movie)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(movies, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        movies = Gson().fromJson(jsonString, listType)
    }
}