package com.example.moviedb_mvvm.Model

data class PopMovieFormatedPoko(
    val poster_path: String?,
    val title : String,
    val genre_ids : List<String>,
    val popularity : Double,
    val release_date : String,
    val id : Int
)