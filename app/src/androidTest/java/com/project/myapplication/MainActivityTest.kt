package com.project.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.myapplication.ui.home.MainActivity
import com.project.myapplication.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadFirstAppeared() {
        onView(withId(R.id.movie_recycler))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_recycler))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadPopularMovies(){
        onView(withId(R.id.category_text_view)).perform(click())
        onView(withId(R.id.popular_text_view)).perform(click())
        onView(withId(R.id.movie_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadTopRatedMovies(){
        onView(withId(R.id.category_text_view)).perform(click())
        onView(withId(R.id.top_rated_text_view)).perform(click())
        onView(withId(R.id.movie_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadNowPlayingMovies(){
        onView(withId(R.id.category_text_view)).perform(click())
        onView(withId(R.id.now_playing_text_view)).perform(click())
        onView(withId(R.id.movie_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadUpComingMovies(){
        onView(withId(R.id.category_text_view)).perform(click())
        onView(withId(R.id.upcoming_text_view)).perform(click())
        onView(withId(R.id.movie_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_recycler))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }
}