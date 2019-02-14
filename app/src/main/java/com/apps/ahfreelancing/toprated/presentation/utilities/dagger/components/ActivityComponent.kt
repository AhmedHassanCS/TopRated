package com.apps.ahfreelancing.toprated.presentation.utilities.dagger.components

import android.app.Activity
import android.content.Context
import com.apps.ahfreelancing.toprated.data.api.ApiAccess
import com.apps.ahfreelancing.toprated.presentation.utilities.dagger.modules.ApiModule
import com.apps.ahfreelancing.toprated.presentation.view.fragment.MovieFragment
import com.apps.ahfreelancing.toprated.presentation.view.fragment.TopRatedFragment
import com.apps.ahfreelancing.toprated.presentation.viewModel.MovieViewModel
import com.apps.ahfreelancing.toprated.presentation.viewModel.TopRatedViewModel
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 * Created by Ahmed Hassan on 2/12/2019.
 */

@Singleton
@Component(modules = [ApiModule::class])
interface ActivityComponent {

    //injection functions
    fun inject(topRatedFragment: TopRatedFragment)

    fun inject(movieFragment: MovieFragment)

    //dependencies functions
    fun activity(): Activity

    fun retrofit(): Retrofit

    fun topRatedViewModel() : TopRatedViewModel

    fun movieViewModel() : MovieViewModel
}