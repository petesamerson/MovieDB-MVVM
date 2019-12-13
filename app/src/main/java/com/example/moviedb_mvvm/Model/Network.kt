package com.example.moviedb_mvvm.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    companion object {
        var movies = MutableLiveData<MutableList<PopMovieFormatedPoko>>()
        const val TAG = "Network"
        var curPage:Int = 1
        lateinit var genreMap: Map<Int, String>

        fun retrofitPopMovies():LiveData<MutableList<PopMovieFormatedPoko>>{
            val retrofit = initRetrofit()
            getGenreMapAndPopMovies(retrofit)
            return movies
        }

        fun getGenreMapAndPopMovies(retrofit: Retrofit){
            val genreApiInterface : GenresApiInterface = retrofit.create(GenresApiInterface::class.java)
            genreApiInterface.getGenres("e078841d2516ec23352636fccdad7958","en-US")
                .enqueue(object : Callback<GenreListPoko> {
                    override fun onFailure(call: Call<GenreListPoko>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<GenreListPoko>,
                        response: Response<GenreListPoko>
                    ) {
                        val genres = response.body()!!.genres
                        var genreMutableMap = mutableMapOf<Int,String>()
                        for (g in genres){
                            genreMutableMap.put(g.id,g.name)
                        }
                        genreMap = genreMutableMap.toMap()
                        getPopularMovies(retrofit,genreMap)
                        //com.getData(genreMap)
                    }
                })

        }
        fun getPopularMovies(retrofit: Retrofit,genreMap:Map<Int,String>){
            val popApiInterface: PopularityApiInterface = retrofit.create(PopularityApiInterface::class.java)
            popApiInterface.getMovies("e078841d2516ec23352636fccdad7958","en-US","1")
                .enqueue(object : Callback<PopListPoko>{
                    override fun onFailure(call: Call<PopListPoko>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<PopListPoko>,
                        response: Response<PopListPoko>
                    ) {

                        movies.value = mutableListOf()
                        for(movie in response.body()!!.results)
                            movies.value!!.add(convertGenres(genreMap,movie))
                        curPage++

//                        for (i in 2 .. response.body()!!.total_pages){
////                            getPageData(i,popApiInterface,genreMap)
//                        }
                        //moviesRaw.observe(this,)

                    }
                })
        }
        fun retrofitGetNextPage():LiveData<MutableList<PopMovieFormatedPoko>>{
            val retrofit:Retrofit = initRetrofit()
            val popApiInterface: PopularityApiInterface = retrofit.create(PopularityApiInterface::class.java)
            popApiInterface.getMovies(
                "e078841d2516ec23352636fccdad7958","en-US",curPage.toString())
                .enqueue(object : Callback<PopListPoko>{
                    override fun onFailure(call: Call<PopListPoko>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<PopListPoko>,
                        response: Response<PopListPoko>
                    ) {
                        if(response.body()!=null){
                            for(movie in response.body()!!.results) {
                                movies.value!!.add(convertGenres(genreMap, movie))
                                Log.d(TAG,movie.poster_path)
                            }
                        }
                        curPage++
                        //listener.getDataFromNetwork(movies)
                    }

                })
            return movies
        }
        fun convertGenres(genreMap:Map<Int,String>,movie: PopMoviePoko): PopMovieFormatedPoko {
            val genres = mutableListOf<String>()
            for (id in movie.genre_ids){
                genres.add(genreMap[id]!!)
            }
            return PopMovieFormatedPoko(
                movie.poster_path,
                movie.title,
                genres,
                movie.popularity,
                movie.release_date,
                movie.id
            )

        }

        fun retrofitMovie(id:Int): MutableLiveData<MoviePoko>{
            val movie = MutableLiveData<MoviePoko>()
            val retrofit = initRetrofit()
            val apiInterface: MovieApiInterface = retrofit.create(MovieApiInterface::class.java)
            apiInterface.getMovie(
                id,"e078841d2516ec23352636fccdad7958","en-US")
                .enqueue(object : Callback<MoviePoko>{
                    override fun onFailure(call: Call<MoviePoko>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<MoviePoko>,
                        response: Response<MoviePoko>
                    ) {
                        Log.d(TAG,response.body().toString())
                        movie.value = response.body()!!
                    }

                })
            return movie
        }
        fun initRetrofit():Retrofit{
            val retrofit = Retrofit.Builder().baseUrl(
                "https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    }
}