package org.wit.assignment1.activities

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.assignment1.R
import org.wit.assignment1.helpers.readImage
import org.wit.assignment1.helpers.readImageFromPath
import org.wit.assignment1.helpers.showImagePicker
import org.wit.assignment1.main.MainApp
import org.wit.assignment1.models.Location
import org.wit.assignment1.models.movieListModel
import java.text.SimpleDateFormat
import java.util.*



class MovieActivity : AppCompatActivity(), AnkoLogger {

    /**
     * The onCreate method handles both adding new movies to the list and updating them
     */
    val IMAGE_REQUEST = 1
    var movie = movieListModel()
    lateinit var app: MainApp
    val LOCATION_REQUEST = 2
    var edit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        val datePicker = findViewById<DatePicker>(R.id.movieReleaseDate)
        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month

        }
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        if (intent.hasExtra("movie_edit")) {
            edit = true
            movie = intent.extras?.getParcelable<movieListModel>("movie_edit")!!
            movieTitle.setText(movie.title)
            movieGenre.setText(movie.genre)
            movieDirector.setText(movie.director)
            datePicker.init(movie.year, movie.month - 1, movie.day, null)
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
                location.lat = movie.lat
                location.lng = movie.lng
                location.zoom = movie.zoom
            }
            startActivityForResult(
                intentFor<MapsActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
        }

        btnAdd.setOnClickListener() {
            movie.title = movieTitle.text.toString()
            movie.genre = movieGenre.text.toString()
            movie.director = movieDirector.text.toString()
            movie.year = datePicker.year
            movie.month = datePicker.month + 1
            movie.day = datePicker.dayOfMonth
            if (movie.title.isEmpty()) {
                toast(R.string.enter_movie_title)
            }
            if (movie.genre.isEmpty()) {
                toast(R.string.enter_movie_genre)
            } else {
                if (edit) {
                    app.movies.update(movie.copy())
                } else {
                    app.movies.create(movie.copy())
                }
            }
            info("Add Button Pressed: $movieTitle $movieGenre $movieDirector")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }

    /**
     * This method handles the delete and cancel buttons
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.movies.delete(movie)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This method sets the visibility of the movies to true
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * When onActivityResult is called the image String gets passes through to an Image
     * The location data is taken in and added to the movie's location data
     */
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