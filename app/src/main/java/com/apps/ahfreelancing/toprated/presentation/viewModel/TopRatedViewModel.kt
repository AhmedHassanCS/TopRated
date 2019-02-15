package com.apps.ahfreelancing.toprated.presentation.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import com.apps.ahfreelancing.toprated.domain.usecase.TopRatedUseCase
import com.google.gson.Gson
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
class TopRatedViewModel @Inject constructor(private val topRatedUseCase: TopRatedUseCase) :
    BaseViewModel<ArrayList<MovieModel>>(topRatedUseCase) {

    private val moviesList = mutableListOf<MovieModel>()
    private val movies = MutableLiveData<List<MovieModel>>()

    init {
        //Initialize the value of the LiveData with an empty list
        movies.value = moviesList
    }

    fun getTopRatedMovies(): LiveData<List<MovieModel>> {
        return movies
    }

    fun updateTopRatedMovies() {
        moviesList.clear()
        topRatedUseCase.setPageNum(1)
        topRatedUseCase.execute(MoviesListObserver())
    }

    fun getPage(pageNum: Int) {
        topRatedUseCase.setPageNum(pageNum)
        topRatedUseCase.execute(MoviesListObserver())
    }

    private inner class MoviesListObserver : DisposableObserver<ArrayList<MovieModel>>() {
        override fun onError(e: Throwable) {
            //return null to indicate network error
            movies.value = null
            topRatedUseCase.dispose()
        }

        override fun onComplete() {
            topRatedUseCase.dispose()
        }

        override fun onNext(latestList: ArrayList<MovieModel>) {
            Log.d("Response List: ", Gson().toJson(latestList))
            moviesList.clear()
            moviesList.addAll(latestList)
            //update the live data value to notify the observer
            movies.value = moviesList
            topRatedUseCase.dispose()
        }


    }
}
