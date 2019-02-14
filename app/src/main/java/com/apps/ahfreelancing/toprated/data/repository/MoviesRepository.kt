package com.apps.ahfreelancing.toprated.data.repository

import com.apps.ahfreelancing.toprated.data.api.ApiAccess
import com.apps.ahfreelancing.toprated.data.convertor.MovieConverter
import com.apps.ahfreelancing.toprated.domain.model.MovieModel
import io.reactivex.Observable

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
class MoviesRepository (private val apiAccess: ApiAccess) {

    fun getTopRatedMovies() : Observable<ArrayList<MovieModel>>{
        //mapper to allow the observable to convert entities to models
        val mapper = MovieConverter()
        return apiAccess.getTopRatedMovies().map(mapper::convertEntitiestoModels)
    }

    fun getMovieDetails(id: Int) : Observable<MovieModel>{
        val mapper = MovieConverter()
        return apiAccess.getMovieDetails(id).map(mapper::convertEntityToModel)
    }
}