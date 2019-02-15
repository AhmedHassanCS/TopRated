package com.apps.ahfreelancing.toprated.presentation.view.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.apps.ahfreelancing.toprated.R
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import com.apps.ahfreelancing.toprated.presentation.view.activity.MainActivityCallback
import com.squareup.picasso.Picasso

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
class MoviesAdapter(val movies : ArrayList<MovieModel>, private val context: Context, val callback : MainActivityCallback) : RecyclerView.Adapter<MovieHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.movie_card_layout, viewGroup, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, index: Int) {
        super.onViewAttachedToWindow(holder)
        holder.bind(context as Activity, movies[index], callback)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}



  class MovieHolder (val view: View) : RecyclerView.ViewHolder (view){

    @BindView(R.id.poster_image_view) lateinit var posterImageView: ImageView
    @BindView(R.id.title_text_view) lateinit var titleTextView: TextView
    @BindView(R.id.genres_text_view) lateinit var genreTextView: TextView

    fun bind(activity: Activity, movie: MovieModel, callback : MainActivityCallback){

        //bind resources of the view
        ButterKnife.bind(this, view)

        //load movie poster
        Picasso.with(activity)
            .load(movie.posterURL)
            .placeholder(R.drawable.ic_launcher_background)
            .into(posterImageView)


        titleTextView.text = movie.name
        if(movie.genres!= null && movie.genres!!.size !=0)
            genreTextView.text = movie.genres!!.joinToString(separator = ", ")

        posterImageView.setOnClickListener {
            activity.title = movie.name
            callback.openMovieFragment(movie.id!!, movie.posterURL!!)
        }

        titleTextView.setOnClickListener {
                activity.title = movie.name
                callback.openMovieFragment(movie.id!!, movie.posterURL!!)
            }
    }
}