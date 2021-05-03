package org.wit.assignment1.models
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import kotlin.math.ln

class MovieDBStore(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION), MovieStore
{
    /**
     * creates the database
     */
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MOVIES_TABLE = ("CREATE TABLE " +
                TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE
                + " TEXT," + COLUMN_GENRE + " TEXT," +
                COLUMN_DIRECTOR + " TEXT," +
                COLUMN_DAY + " INTEGER," +
                COLUMN_MONTH + " INTEGER," +
                COLUMN_YEAR + " INTEGER," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_LAT + " DOUBLE," +
                COLUMN_LNG + " DOUBLE," +
                COLUMN_ZOOM + " FLOAT" +
                ")")
        db.execSQL(CREATE_MOVIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        onCreate(db)
    }

    /**
     * The values for each column are declared here
     */
    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "movieDB.db"
        val TABLE_MOVIES = "movies"

        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_GENRE = "genre"
        val COLUMN_DIRECTOR = "director"
        val COLUMN_DAY = "day"
        val COLUMN_MONTH = "month"
        val COLUMN_YEAR = "year"
        val COLUMN_IMAGE = "image"
        val COLUMN_LAT = "lat"
        val COLUMN_LNG = "lng"
        val COLUMN_ZOOM = "zoom"
    }

    /**
     * This method gets all of the movies from the table and returns them
     */
    override fun findAll(): List<movieListModel> {
        val query = "SELECT * FROM $TABLE_MOVIES"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        val movies = ArrayList<movieListModel>()

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = Integer.parseInt(cursor.getString(0)).toLong()
                val title = cursor.getString(1)
                val genre = cursor.getString(2)
                val director = cursor.getString(3)
                val day = cursor.getInt(4)
                val month = cursor.getInt(5)
                val year = cursor.getInt(6)
                val image = cursor.getString(7)
                val lat = cursor.getDouble(8)
                val lng = cursor.getDouble(9)
                val zoom = cursor.getFloat(10)
                movies.add(movieListModel(id, title = title, genre = genre, director = director, day = day, month = month, year = year, image = image, lat = lat, lng = lng, zoom = zoom))
                cursor.moveToNext()
            }
            cursor.close()
        }
        db.close()
        return movies
    }

    /**
     * The create method adds all of the movie's attributes to the database
     */
    override fun create(movie: movieListModel) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, movie.title)
        values.put(COLUMN_GENRE, movie.genre)
        values.put(COLUMN_DIRECTOR, movie.director)
        values.put(COLUMN_DAY, movie.day)
        values.put(COLUMN_MONTH, movie.month)
        values.put(COLUMN_YEAR, movie.year)
        values.put(COLUMN_IMAGE, movie.image)
        values.put(COLUMN_LAT, movie.lat)
        values.put(COLUMN_LNG, movie.lng)
        values.put(COLUMN_ZOOM, movie.zoom)

        val db = this.writableDatabase

        db.insert(TABLE_MOVIES, null, values)
        db.close()
    }

    /**
     * The update function gets the currently inputted items for movie.attribute and updates the table
     */
    // https://www.javatpoint.com/kotlin-android-sqlite-tutorial <--- The absolute heroes who helped me with the update and delete
    override fun update(movie: movieListModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, movie.id)
        values.put(COLUMN_TITLE, movie.title)
        values.put(COLUMN_GENRE, movie.genre)
        values.put(COLUMN_DIRECTOR, movie.director)
        values.put(COLUMN_DAY, movie.day)
        values.put(COLUMN_MONTH, movie.month)
        values.put(COLUMN_YEAR, movie.year)
        values.put(COLUMN_IMAGE, movie.image)
        values.put(COLUMN_LAT, movie.lat)
        values.put(COLUMN_LNG, movie.lng)
        values.put(COLUMN_ZOOM, movie.zoom)

        val updated = db.update(TABLE_MOVIES, values,"_id="+ movie.id,null)
        db.close()

    }

    /**
     * The delete function gets the movie by its id and removes it from the table
     */
    override fun delete(movie: movieListModel) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, movie.id)

        val deleted = db.delete(TABLE_MOVIES,"_id="+movie.id,null)
        db.close()
    }

}