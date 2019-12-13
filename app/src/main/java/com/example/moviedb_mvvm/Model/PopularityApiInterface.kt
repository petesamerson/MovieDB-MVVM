package com.example.moviedb.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularityApiInterface {
    @GET("3/movie/popular")
    fun getMovies(
        @Query("api_key") api_key : String,
        @Query("language") language: String,
        @Query("page") page : String
    ): Call<PopListPoko>
}