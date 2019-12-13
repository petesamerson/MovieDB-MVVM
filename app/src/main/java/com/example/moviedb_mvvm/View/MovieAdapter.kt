package com.example.moviedb.View

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb_mvvm.Model.PopMovieFormatedPoko
import com.example.moviedb_mvvm.R
import com.squareup.picasso.Picasso

class MovieAdapter internal constructor(context: Context)
    :RecyclerView.Adapter<MovieViewHolder>(){

    private val inflater = LayoutInflater.from(context)
    private val context:Context = context
    var movies = emptyList<PopMovieFormatedPoko>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item,parent,false)
        return MovieViewHolder(itemView)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Picasso.get().load(context.resources.getString(R.string.image_base_url).plus(
                movies[position].poster_path)).into(holder.ivPoster)
        Log.d(TAG,context.resources.getString(R.string.image_base_url).plus(
            movies[position].poster_path))
        holder.tvTitle.text = movies[position].title
        holder.tvYear.text = movies[position].release_date.substring(0,4)
        holder.tvPopularity.text = context.resources.getString(R.string.popularity_base_text).plus(
                movies[position].popularity.toString())
        holder.tvGenres.text = movies[position].genre_ids.toString()
        holder.bindListener(movies[position].id,context as MovieViewHolder.MovieSelector)
    }
    companion object{
        val TAG = "MovieAdapter"
    }
}