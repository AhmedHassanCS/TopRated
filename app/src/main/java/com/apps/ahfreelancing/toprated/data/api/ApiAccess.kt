package com.apps.ahfreelancing.toprated.data.api

import com.apps.ahfreelancing.toprated.data.entity.ApiResponseEntity
import com.apps.ahfreelancing.toprated.data.entity.MovieEntity
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
class ApiAccess @Inject constructor(retrofit: Retrofit) {

    private val api : TmdbApi = retrofit.create(TmdbApi::class.java)

    //Didn't move api key to local properties to make the evaluator be able to use it easily
    private val apiKey : String = "9f91f0eefb25782613be1ddf5e66dfde"

    fun getTopRatedMovies(pageNum : Int) : Observable<ArrayList<MovieEntity>>{

        return Observable.create { emitter ->

            val call = api.getTopRatedMovies(apiKey, pageNum)
            val moviesList = ArrayList<MovieEntity>()

            call.enqueue(object : Callback<ApiResponseEntity> {
                override fun onResponse(call: Call<ApiResponseEntity>?, response: Response<ApiResponseEntity>?) {
                    moviesList.addAll(response!!.body().results)
                    emitter.onNext(moviesList)
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<ApiResponseEntity>?, t: Throwable?) {
                    emitter.onError(t!!)
                }

            })
        }

    }

    fun getMovieDetails(id: Int) : Observable<MovieEntity>{
        return Observable.create { emitter ->

            val call = api.getMovie(id, apiKey)
            call.enqueue(object : Callback<MovieEntity> {
                override fun onResponse(call: Call<MovieEntity>?, response: Response<MovieEntity>?) {
                    emitter.onNext(response!!.body())
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<MovieEntity>?, t: Throwable?) {
                    emitter.onError(t!!)
                }

            })
        }
    }
}