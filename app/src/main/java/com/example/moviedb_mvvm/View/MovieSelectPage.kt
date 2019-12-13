package com.example.moviedb_mvvm.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb_mvvm.R
import com.example.moviedb_mvvm.ViewModel.MovieViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_detail_activity.*

class MovieSelectPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)

        val viewModel = ViewModelProvider(this)
            .get(MovieViewModel::class.java)

        iv_back.setOnClickListener {
            returnToMain()
        }
        viewModel.getSelectData(intent.getIntExtra("id",0)).observe(
            this,
            Observer {
                Picasso.get().load(resources.getString(R.string.image_base_url_original).plus(
                    it.poster_path)).into(iv_poster_select)
                Log.d("Select",resources.getString(R.string.image_base_url_original).plus(
                    it.poster_path))
                tv_title_select.text = it.title
                tv_popularity_select.text = resources.getString(
                    R.string.popularity_base_text).plus(it.popularity)
                tv_runtime.text = it.runtime.toString().plus(
                    resources.getString(R.string.runtime_base_text))
                tv_year_select.text= it.release_date
                var genres = mutableListOf<String>()
                for (genre in it.genres){
                    genres.add(genre.name)
                }
                tv_genres_select.text = genres.toString()
                tv_overview.text = it.overview
            })
    }
    private fun returnToMain(){
        val mainIntent = Intent()
        mainIntent.setClass(this,MainActivity::class.java)
        startActivity(mainIntent)
    }
}
