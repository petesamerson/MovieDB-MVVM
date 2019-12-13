package com.example.moviedb.View

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb_mvvm.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class MovieViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
    val tvTitle: TextView  = itemView.findViewById(R.id.tv_title)
    val tvYear: TextView = itemView.findViewById(R.id.tv_year)
    val tvPopularity: TextView = itemView.findViewById(R.id.tv_popularity)
    val tvGenres: TextView = itemView.findViewById(R.id.tv_genres)

    fun bindListener(id:Int,moveSelector: MovieSelector){
        itemView.setOnClickListener{
            moveSelector.openMovie(id)
        }
    }
    interface MovieSelector{
        fun openMovie(id:Int)
    }
}