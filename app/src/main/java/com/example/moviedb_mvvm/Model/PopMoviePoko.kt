package com.example.moviedb.Model

data class PopMoviePoko(
    val poster_path: String,
    val title : String,
    val genre_ids : List<Int>,
    val popularity : Double,
    val release_date : String,
    val id : Int
)
