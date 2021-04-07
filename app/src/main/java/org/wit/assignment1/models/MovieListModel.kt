package org.wit.assignment1.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class movieListModel(var id: Long = 0,
                          var title: String = "",
                          var genre: String = "",
                          var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable