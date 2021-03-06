package com.apps.ahfreelancing.toprated.domain.usecase

import com.apps.ahfreelancing.toprated.data.repository.MoviesRepository
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */

class TopRatedUseCase @Inject constructor(private val moviesRepository: MoviesRepository)
    : BaseUseCase<ArrayList<MovieModel>>(moviesRepository) {

    private var pageNum : Int = 1

    override fun execute(observer: DisposableObserver<ArrayList<MovieModel>>) {
        disposable = moviesRepository.getTopRatedMovies(pageNum)
            .subscribeWith(observer)
    }

    fun setPageNum(pageNum: Int){
        this.pageNum = pageNum
    }

}