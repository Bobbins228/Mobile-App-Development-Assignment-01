package org.wit.assignment1.models

interface MovieStore {
    fun findAll(): List<movieListModel>
    fun create(movie: movieListModel)
    fun update(movie: movieListModel)
}