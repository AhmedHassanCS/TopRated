package com.apps.ahfreelancing.toprated.domain.usecase

import com.apps.ahfreelancing.toprated.data.repository.MoviesRepository
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by Ahmed Hassan on 2/14/2019.
 */
class MovieDetailsUseCase @Inject constructor(private val repository: MoviesRepository)
    : BaseUseCase<MovieModel>(repository) {

    private var movieId : Int = 0

    override fun execute(observer: DisposableObserver<MovieModel>) {
        disposable = repository.getMovieDetails(movieId).subscribeWith(observer)
    }

    fun setMovieId(movieId : Int){
        this.movieId = movieId
    }
}