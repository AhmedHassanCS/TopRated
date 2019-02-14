package com.apps.ahfreelancing.toprated.presentation.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import com.apps.ahfreelancing.toprated.domain.usecase.MovieDetailsUseCase
import com.google.gson.Gson
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by Ahmed Hassan on 2/14/2019.
 */
class MovieViewModel @Inject constructor(private val movieUseCase: MovieDetailsUseCase) :
    BaseViewModel<MovieModel>(movieUseCase) {

    private val movieLiveData = MutableLiveData<MovieModel>()


    fun getMovieDetails(movieId: Int): LiveData<MovieModel> {
        updateTopRatedMovies(movieId)
        return movieLiveData
    }

    private fun updateTopRatedMovies(movieId: Int) {
        //Call the use case
        movieUseCase.setMovieId(movieId)
        movieUseCase.execute(MovieObserver())
    }

    private inner class MovieObserver : DisposableObserver<MovieModel>() {
        override fun onError(e: Throwable) {
            //return null to indicate network error
            movieLiveData.value = null
            movieUseCase.dispose()
        }

        override fun onComplete() {
            movieUseCase.dispose()
        }

        override fun onNext(latestMovie: MovieModel) {
            Log.d("Response Model: ", Gson().toJson(latestMovie))
            movieLiveData.value = latestMovie
            movieUseCase.dispose()
        }


    }
}