package com.example.moviedb.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("3/movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movieID:Int,
        @Query("api_key") api_key : String,
        @Query("language") language: String
    ): Call<MoviePoko>
}