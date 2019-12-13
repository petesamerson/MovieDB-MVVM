package com.example.moviedb.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiInterface {
    @GET("3/genre/movie/list")
    fun getGenres(
        @Query("api_key") api_key:String,
        @Query("language") language:String
    ): Call<GenreListPoko>
}