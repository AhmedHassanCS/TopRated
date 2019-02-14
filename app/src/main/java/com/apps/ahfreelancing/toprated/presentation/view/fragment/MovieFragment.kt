package com.apps.ahfreelancing.toprated.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.apps.ahfreelancing.toprated.R
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import com.apps.ahfreelancing.toprated.presentation.viewModel.MovieViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject


class MovieFragment : Fragment() {

    @Inject
    lateinit var movieViewModel: MovieViewModel
    //Movie basic data that are displayed until all data are loaded
    private var movieId: Int = 0
    private var poster: String? = null

    //Binding Fragment UI components
    @BindView(R.id.poster_image_view)
    lateinit var posterImageView: ImageView
    @BindView(R.id.average_vote_text_view)
    lateinit var averageVoteTextView: TextView
    @BindView(R.id.vote_count_text_view)
    lateinit var voteCountTextView: TextView
    @BindView(R.id.genres_text_view)
    lateinit var genresTextView: TextView
    @BindView(R.id.language_text_view)
    lateinit var languageTextView: TextView
    @BindView(R.id.release_date_text_view)
    lateinit var releaseDateTextView: TextView

    @BindView(R.id.movie_outer_layout)
    lateinit var outerLinearLayout: LinearLayout
    @BindView(R.id.movie_progress_bar)
    lateinit var movieProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        if (savedInstanceState != null) {
            movieId = savedInstanceState.getInt("movie_id")
            poster = savedInstanceState.getString("movie_poster")
        }
        ButterKnife.bind(this, view)
        movieProgressBar.visibility = View.VISIBLE
        movieProgressBar.animate()

        showPoster(poster)
        initViewModel()
        manageOrientation()
        return view
    }

    private fun manageOrientation() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            outerLinearLayout.orientation = LinearLayout.HORIZONTAL
            posterImageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.MATCH_PARENT
            )
        } else outerLinearLayout.orientation = LinearLayout.VERTICAL
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("movie_id", movieId)
        outState.putString("movie_poster", poster)
    }

    private fun initViewModel() {
        //Observe on the LiveData returned from the ModelView
        movieViewModel.getMovieDetails(movieId).observe(this, Observer { newMovie ->
            //Null means the error function has been called
            if (newMovie == null) {
                Toast.makeText(activity, "Network Failure", Toast.LENGTH_LONG).show()
                movieProgressBar.visibility = View.GONE
                return@Observer
            }

            movieProgressBar.visibility = View.GONE
            bindMovieModel(newMovie!!)
        })
    }

    private fun bindMovieModel(movieModel: MovieModel) {
        activity!!.title = movieModel.name
        averageVoteTextView.text = getString(R.string.avg_vote, movieModel.averageVote.toString())
        voteCountTextView.text = getString(R.string.total_votes, movieModel.voteCount.toString())
        genresTextView.text = getString(R.string.genres, movieModel.genres!!.joinToString(separator = ", "))
        languageTextView.text = getString(R.string.lang, movieModel.language)
        releaseDateTextView.text = getString(R.string.release_date, movieModel.releaseDate)
    }

    fun setParameters(movieId: Int, poster: String) {
        this.movieId = movieId
        this.poster = poster
    }

    private fun showPoster(poster: String?) {
        if (poster != null)
            Picasso.with(activity)
                .load(poster.replace("w342", "w500"))
                .placeholder(R.drawable.ic_launcher_background)
                .into(posterImageView)
    }

}
