package com.project.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.util.LiveDataTestUtil.observeForTesting
import com.project.myapplication.util.PagedListUtil
import com.project.myapplication.domain.usecase.favorite.FavoriteUseCase
import com.project.myapplication.ui.favorite.FavoriteViewModel
import com.project.myapplication.utils.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var favoriteUseCase: FavoriteUseCase

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = FavoriteViewModel(favoriteUseCase)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getFavoriteMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(
                DataDummy.getMoviesListDummy().map {
                    RoomMovieEntity(it.id!!, it.name!!, it.description!!, it.rating!!,
                            posterPath = it.posterPath?:"", releaseDate = it.releaseDate)
                })
        doAnswer {
            val liveData = MutableLiveData<PagedList<RoomMovieEntity>>()
            liveData.value = dummyMovies
            liveData
        }.`when`(favoriteUseCase).getFavoriteMovies()
        val movieLiveData = viewModel.getFavoriteMovies()
        verify(favoriteUseCase).getFavoriteMovies()
        movieLiveData.observeForTesting{
            val moviesEntities = movieLiveData.value
            Assert.assertNotNull(moviesEntities)
            Assert.assertEquals(dummyMovies.size, moviesEntities?.size ?: 0)
        }
    }

}