package com.project.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
class DetailActivityTest {
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
    fun loadDetailMovies() {
        onView(withId(R.id.movie_recycler)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_card)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.movie_image_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.title_text_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.release_date_text_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.like_button)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.description_text_view)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.review_recycler)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun performFavoriteClicked() {
        onView(withId(R.id.movie_recycler)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_card)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.like_button)).perform(click())
    }
}