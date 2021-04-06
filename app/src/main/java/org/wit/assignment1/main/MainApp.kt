package org.wit.assignment1.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.assignment1.activities.MovieActivity
import org.wit.assignment1.models.MovieMemStore
import org.wit.assignment1.models.movieListModel

class MainApp : Application(), AnkoLogger {

    //val movies = ArrayList<movieListModel>()
    val movies = MovieMemStore()

    override fun onCreate() {
        super.onCreate()
        info("My Movie List started")
        movies.create(movieListModel(0, "Hot Fuzz","Comedy"))
        movies.create(movieListModel(1,"John Wick", "Action"))
        movies.create(movieListModel(2,"Cars", "Family"))
    }
}