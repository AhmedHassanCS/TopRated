package com.apps.ahfreelancing.toprated.presentation.view.fragment

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.apps.ahfreelancing.toprated.R
import com.apps.ahfreelancing.toprated.presentation.view.activity.MainActivity
import com.apps.ahfreelancing.toprated.presentation.view.activity.MainActivityCallback
import com.apps.ahfreelancing.toprated.presentation.view.adapter.MoviesAdapter
import com.apps.ahfreelancing.toprated.presentation.viewModel.TopRatedViewModel
import javax.inject.Inject

class TopRatedFragment : Fragment() {

    //Bind recycler view using ButterKnife shortcut annotation
    @BindView(R.id.movies_recycler_view)
    lateinit var moviesRecyclerView: RecyclerView

    @BindView(R.id.movies_progress_bar)
    lateinit var moviesProgressBar: ProgressBar


    //Inject the ViewModel using dagger dependency injection, Creation is in [ApiModule]
    @Inject
    lateinit var topRatedViewModel: TopRatedViewModel

    //Declaration of the adapter to be initialized then notified later
    lateinit var adapter: MoviesAdapter

    //mainActivityCallback variable will be initialized by the activity
    lateinit var mainActivityCallback: MainActivityCallback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_top_rated, container, false)

        //bind this class with the inflated view using ButterKnife
        ButterKnife.bind(this, view)

        //view progressbar
        moviesProgressBar.visibility = View.VISIBLE
        moviesProgressBar.animate()

        initRecycler()
        initViewModel()

        return view
    }

    fun setParameters(mainActivityCallback: MainActivityCallback) {
        this.mainActivityCallback = mainActivityCallback
    }

    private fun initRecycler() {
        //initialize the recycler view if it's not initialized

        //determine spans number based on orientation
        val spans: Int
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            spans = 4
        else
            spans = 2

        //initialize layoutManager if it's not initialized
        if (moviesRecyclerView.layoutManager == null)
            moviesRecyclerView.layoutManager = GridLayoutManager(activity, spans)


        if (moviesRecyclerView.adapter == null) {
            adapter = MoviesAdapter(ArrayList(emptyList()), activity as Context, mainActivityCallback)
            moviesRecyclerView.adapter = adapter

            //Top Rated Movies will be updated only once with the creation of the adapter
            topRatedViewModel.updateTopRatedMovies()
        }
    }

    private fun initViewModel() {
        //Observe on the LiveData returned from the ModelView
        topRatedViewModel.getTopRatedMovies().observe(this, Observer { newMovies ->

            //Null means the error function has been called in the view model
            if(newMovies == null){
                Toast.makeText(activity, "Network Failure", Toast.LENGTH_LONG).show()
                moviesProgressBar.visibility = View.GONE
                return@Observer
            }

            if(newMovies.isNotEmpty())
                moviesProgressBar.visibility = View.GONE

            adapter.movies.clear()
            adapter.movies.addAll(newMovies)

            adapter.notifyDataSetChanged()
        })
    }

}
