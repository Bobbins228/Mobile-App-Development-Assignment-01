package org.wit.assignment1.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.assignment1.activities.MovieActivity
import org.wit.assignment1.models.movieListModel

class MainApp : Application(), AnkoLogger {

    val movies = ArrayList<movieListModel>()

    override fun onCreate() {
        super.onCreate()
        info("My Movie List started")
        movies.add(movieListModel("Hot Fuzz", "Comedy"))
        movies.add(movieListModel("John Wick", "Action"))
        movies.add(movieListModel("Cars", "Family"))
    }
}