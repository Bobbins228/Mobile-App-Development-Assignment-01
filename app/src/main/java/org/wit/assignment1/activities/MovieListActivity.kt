package org.wit.assignment1.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.assignment1.R
import org.wit.assignment1.main.MainApp
import org.wit.assignment1.models.movieListModel


class MovieListActivity : AppCompatActivity(), MovieListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //recyclerView.adapter = MovieAdapter(app.movies)
        recyclerView.adapter = MovieAdapter(app.movies.findAll(), this)

        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<MovieActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMovieClick(movie: movieListModel) {
        startActivityForResult(intentFor<MovieActivity>().putExtra("movie_edit", movie), 0)
    }
}

