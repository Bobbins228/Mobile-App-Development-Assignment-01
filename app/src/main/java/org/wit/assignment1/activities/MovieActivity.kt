package org.wit.assignment1.activities

import android.content.Intent
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
import org.jetbrains.anko.intentFor
import org.wit.assignment1.helpers.readImage
import org.wit.assignment1.helpers.readImageFromPath
import org.wit.assignment1.helpers.showImagePicker
import org.wit.assignment1.main.MainApp
import org.wit.assignment1.models.Location


// PROBLEM WHERE THE FIRST MOVIE IN LIST DIES IF I MAKE A NEW MOVIE THEN UPDATE IT LATER


class MovieActivity : AppCompatActivity(), AnkoLogger {
    val IMAGE_REQUEST = 1
    var movie = movieListModel()
    lateinit var app : MainApp
    val LOCATION_REQUEST = 2
    //var location = Location(34.09834, -118.32674, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        var edit = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        if (intent.hasExtra("movie_edit")) {
            edit = true
            movie = intent.extras?.getParcelable<movieListModel>("movie_edit")!!
            movieTitle.setText(movie.title)
            movieGenre.setText(movie.genre)
            movieImage.setImageBitmap(readImageFromPath(this, movie.image))
            btnAdd.setText(R.string.save_movie)
            movieImage.setImageBitmap(readImageFromPath(this, movie.image))
            if (movie.image != null) {
                chooseImage.setText(R.string.change_movie_image)
            }
        }

        movieLocation.setOnClickListener {
            val location = Location(34.09834, -118.32674, 15f)
            if (movie.zoom != 0f) {
                location.lat =  movie.lat
                location.lng = movie.lng
                location.zoom = movie.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        btnAdd.setOnClickListener() {
            movie.title = movieTitle.text.toString()
            movie.genre = movieGenre.text.toString()
            if (movie.title.isEmpty()) {
                toast(R.string.enter_movie_title)
            } else {
                if(edit) {
                    app.movies.update(movie.copy())
                } else {
                    app.movies.create(movie.copy())
                }
            }
            info("Add Button Pressed: $movieTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    movie.image = data.getData().toString()
                    movieImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_movie_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    movie.lat = location.lat
                    movie.lng = location.lng
                    movie.zoom = location.zoom
                }
            }
        }
    }
}