package org.wit.assignment1.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.assignment1.models.MovieDBStore
import org.wit.assignment1.models.MovieJSONStore
import org.wit.assignment1.models.MovieMemStore
import org.wit.assignment1.models.MovieStore


class MainApp : Application(), AnkoLogger {

    //val movies = ArrayList<movieListModel>()
    lateinit var movies: MovieStore

    //lateinit var movies: MovieStore
    //lateinit

    override fun onCreate() {
        super.onCreate()
        //movies = MovieMemStore()
        //movies = MovieJSONStore(applicationContext)
        info("My Movie List started")
       /* movies.create(movieListModel(0, "Hot Fuzz","Comedy"))
        movies.create(movieListModel(1,"John Wick", "Action"))
        movies.create(movieListModel(2,"Cars", "Family")) */
        movies = MovieDBStore(this, null, null, 1)
        info("MovieApp started: datastore type is ${movies.javaClass}")
    }
}