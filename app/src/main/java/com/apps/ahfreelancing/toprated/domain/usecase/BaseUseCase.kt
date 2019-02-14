package com.apps.ahfreelancing.toprated.domain.usecase

import com.apps.ahfreelancing.toprated.data.repository.MoviesRepository
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

/**
 * Created by Ahmed Hassan on 2/14/2019.
 */

abstract class BaseUseCase<T>(private val moviesRepository: MoviesRepository) {
    lateinit var disposable: Disposable

    abstract fun execute(observer: DisposableObserver<T>)

    fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }
}