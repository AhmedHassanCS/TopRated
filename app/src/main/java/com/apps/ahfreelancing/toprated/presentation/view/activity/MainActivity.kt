package com.apps.ahfreelancing.toprated.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apps.ahfreelancing.toprated.R
import com.apps.ahfreelancing.toprated.presentation.utilities.dagger.components.ActivityComponent
import com.apps.ahfreelancing.toprated.presentation.utilities.dagger.components.DaggerActivityComponent
import com.apps.ahfreelancing.toprated.presentation.utilities.dagger.modules.ApiModule
import com.apps.ahfreelancing.toprated.presentation.view.fragment.MovieFragment
import com.apps.ahfreelancing.toprated.presentation.view.fragment.TopRatedFragment

class MainActivity : AppCompatActivity() {


    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDagger()
        initializeFragment(savedInstanceState)
    }

    //onBackPressed the activity either closes or get the TopRatedFragment again if it was closed
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout)
        if (fragment is MovieFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(fragment)
            transaction.commit()
            title = getString(R.string.app_name)
            if (supportActionBar != null)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            initializeFragment(null)
        } else super.onBackPressed()
    }

    //The supportActionBar back button action is just like back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //TopRatedFragment is considered the home fragment
    private fun initializeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val topRatedFragment = TopRatedFragment()
            topRatedFragment.setParameters(Callback())
            transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            transaction.add(R.id.main_frame_layout, topRatedFragment)
            transaction.commit()

            //inject the fragment with the activity component
            activityComponent.inject(topRatedFragment)
        } else {
            //The application is resumed
            if (supportFragmentManager.findFragmentById(R.id.main_frame_layout) is TopRatedFragment) {
                val topRatedFragment =
                    supportFragmentManager.findFragmentById(R.id.main_frame_layout) as TopRatedFragment
                //Pass the fragment a new callback
                topRatedFragment.setParameters(Callback())
                //inject the fragment with the activity component
                activityComponent.inject(topRatedFragment)
            } else {
                //The case when the loaded fragment is a movie fragment
                if (supportActionBar != null)
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)

                val movieFragment =
                    supportFragmentManager.findFragmentById(R.id.main_frame_layout) as MovieFragment

                activityComponent.inject(movieFragment)
            }
        }
    }

    private fun addMovieFragment(movieId: Int, poster: String) {

        val transaction = supportFragmentManager.beginTransaction()
        val movieFragment = MovieFragment()
        movieFragment.setParameters(movieId, poster)
        activityComponent.inject(movieFragment)
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.main_frame_layout, movieFragment)
        transaction.commit()

        //Show the back button in the action bar
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initDagger() {
        //initialize the dagger activity component
        activityComponent = DaggerActivityComponent
            .builder()
            .apiModule(ApiModule(this))
            .build()
    }

    //Callback implementation from fragment to activity
    inner class Callback : MainActivityCallback {
        override fun openMovieFragment(movieId: Int, poster: String) {
            addMovieFragment(movieId, poster)
        }

    }
}
