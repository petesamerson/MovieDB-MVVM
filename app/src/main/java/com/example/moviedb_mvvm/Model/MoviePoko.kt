package com.example.moviedb.Model

data class MoviePoko(
    val poster_path: String,
    val title : String,
    val genres : List<GenrePoko>,
    val popularity : Double,
    val release_date : String,
    val overview: String,
    val runtime: Int
)
