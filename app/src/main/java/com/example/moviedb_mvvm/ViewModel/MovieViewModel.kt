package com.example.moviedb_mvvm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.Model.MoviePoko
import com.example.moviedb_mvvm.Model.PopMovieFormatedPoko
import com.example.moviedb_mvvm.Model.Network

class MovieViewModel : ViewModel(){
    private lateinit var movies: LiveData<MutableList<PopMovieFormatedPoko>>
    private lateinit var movie: LiveData<MoviePoko>

    fun retrofitPopMovies(){

        movies = Network.retrofitPopMovies()
    }
    fun retrofitMovie(id:Int){
        movie = Network.retrofitMovie(id)
    }

    fun getPopData():LiveData<MutableList<PopMovieFormatedPoko>>{
        retrofitPopMovies()
        return movies
    }
    fun getSelectData(id:Int):LiveData<MoviePoko>{
       retrofitMovie(id)
       return movie
    }
    fun getNextPage():LiveData<MutableList<PopMovieFormatedPoko>>{
        movies = Network.retrofitGetNextPage()
        return movies
    }
    companion object{
        const val TAG = "MovieViewModel"
    }
}