package com.example.moviedb_mvvm.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.View.MovieAdapter
import com.example.moviedb.View.MovieViewHolder
import com.example.moviedb_mvvm.R
import com.example.moviedb_mvvm.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieViewHolder.MovieSelector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel = ViewModelProvider(this)
            .get(MovieViewModel::class.java)


        val adapter = MovieAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getPopData().observe(
            this,
            Observer {
                Log.d(TAG,it.toString())
                adapter.movies = it
            })

        tv_load_more.setOnClickListener {
           viewModel.getNextPage().observe(
               this,
               Observer{
                   adapter.movies = it
               })
        }
    }
    override fun openMovie(id: Int) {
        val openNewActivity = Intent()
        openNewActivity.putExtra("id", id)
        openNewActivity.setClass(this, MovieSelectPage::class.java!!)
        startActivityForResult(openNewActivity, NEW_TASK_REQUEST_CODE)
    }
    companion object{
        const val TAG = "MainActivity"
        const val NEW_TASK_REQUEST_CODE = 812
    }
}
