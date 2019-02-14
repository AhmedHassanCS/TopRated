package com.apps.ahfreelancing.toprated.presentation.utilities.dagger.modules

import android.app.Activity
import com.apps.ahfreelancing.toprated.data.api.ApiAccess
import com.apps.ahfreelancing.toprated.data.repository.MoviesRepository
import com.apps.ahfreelancing.toprated.domain.usecase.MovieDetailsUseCase
import com.apps.ahfreelancing.toprated.domain.usecase.TopRatedUseCase
import com.apps.ahfreelancing.toprated.presentation.viewModel.MovieViewModel
import com.apps.ahfreelancing.toprated.presentation.viewModel.TopRatedViewModel
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
@Module
class ApiModule (val activity: Activity) {

    @Provides
    @Singleton
    internal fun provideActivityContext(): Activity {
        return activity
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        //To log the response for testing
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    @Singleton
    internal fun provideTopRatedViewModel(retrofit: Retrofit): TopRatedViewModel {
        //Provide the viewModel
        return TopRatedViewModel(
            TopRatedUseCase(
                MoviesRepository(
                    ApiAccess(
                        retrofit
                    )))
        )
    }

    @Provides
    @Singleton
    internal fun provideMovieViewModel(retrofit: Retrofit): MovieViewModel {
        //Provide the viewModel
        return MovieViewModel(
            MovieDetailsUseCase(
                MoviesRepository(
                    ApiAccess(
                        retrofit
                    )))
        )
    }
}