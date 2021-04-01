package org.wit.assignment1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.wit.assignment1.R
import org.wit.assignment1.models.movieListModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_movie.*
import org.wit.assignment1.main.MainApp

class MovieActivity : AppCompatActivity(), AnkoLogger {

    var movieList = movieListModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        btnAdd.setOnClickListener() {
            movieList.title = movieTitle.text.toString()
            movieList.genre = movieGenre.text.toString()
            if (movieList.title.isNotEmpty()) {
                app.movies.add(movieList.copy())
                app.movies.forEach {info("add Button Pressed: ${it.title}, ${it.genre}")}
                //=======DELETE ME PLEASE=========
                toast(app.movies.toString())
                //================================
                for (i in app.movies.indices) {
                    info("Movie[$i]:${app.movies[i]}")
                }
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
            else {
                toast ("Please Enter a title")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        return super.onCreateOptionsMenu(menu)
    }
}