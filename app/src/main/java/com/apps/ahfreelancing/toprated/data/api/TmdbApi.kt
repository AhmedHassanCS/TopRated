package com.apps.ahfreelancing.toprated.data.api

import com.apps.ahfreelancing.toprated.data.entity.ApiResponseEntity
import com.apps.ahfreelancing.toprated.data.entity.MovieEntity
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
interface TmdbApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String
                          , @Query("page") page: Int): Call<ApiResponseEntity>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieEntity>

}