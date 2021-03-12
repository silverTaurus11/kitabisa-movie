package com.project.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.util.LiveDataTestUtil.observeForTesting
import com.project.myapplication.util.PagedListUtil
import com.project.myapplication.domain.usecase.main.MovieUseCase
import com.project.myapplication.ui.home.MainViewModel
import com.project.myapplication.utils.DataDummy
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesUseCase: MovieUseCase

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MainViewModel(moviesUseCase)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getPopularMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Success(dummyMovies)
            liveData
        }.`when`(moviesUseCase).getPopularMovies()
        val movieLiveData = viewModel.getPopularMovies()
        verify(moviesUseCase).getPopularMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertNotNull(movieLiveData.value?.data)
            assertEquals(dummyMovies.size, movieLiveData.value?.data?.size)
        }
    }

    @Test
    fun getPopularMovies_Error(){
        val dataError = "DATA ERROR"
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Error(dataError)
            liveData
        }.`when`(moviesUseCase).getPopularMovies()
        val movieLiveData = viewModel.getPopularMovies()
        verify(moviesUseCase).getPopularMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Error<PagedList<MovieEntity>>)
            assertEquals(dataError, movieLiveData.value?.message)
        }
    }

    @Test
    fun getTopRatedMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Success(dummyMovies)
            liveData
        }.`when`(moviesUseCase).getTopRatedMovies()
        val movieLiveData = viewModel.getTopRatedMovies()
        verify(moviesUseCase).getTopRatedMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertNotNull(movieLiveData.value?.data)
            assertEquals(dummyMovies.size, movieLiveData.value?.data?.size)
        }
    }

    @Test
    fun getTopRatedMovies_Error(){
        val dataError = "DATA ERROR"
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Error(dataError)
            liveData
        }.`when`(moviesUseCase).getTopRatedMovies()
        val movieLiveData = viewModel.getTopRatedMovies()
        verify(moviesUseCase).getTopRatedMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Error<PagedList<MovieEntity>>)
            assertEquals(dataError, movieLiveData.value?.message)
        }
    }

    @Test
    fun getUpComingMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Success(dummyMovies)
            liveData
        }.`when`(moviesUseCase).getUpComingMovies()
        val movieLiveData = viewModel.getUpComingMovies()
        verify(moviesUseCase).getUpComingMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertNotNull(movieLiveData.value?.data)
            assertEquals(dummyMovies.size, movieLiveData.value?.data?.size)
        }
    }

    @Test
    fun getUpComingMovies_Error(){
        val dataError = "DATA ERROR"
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Error(dataError)
            liveData
        }.`when`(moviesUseCase).getUpComingMovies()
        val movieLiveData = viewModel.getUpComingMovies()
        verify(moviesUseCase).getUpComingMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Error<PagedList<MovieEntity>>)
            assertEquals(dataError, movieLiveData.value?.message)
        }
    }

    @Test
    fun getNowPlayingMovies_Success(){
        val dummyMovies = PagedListUtil.mockPagedList(DataDummy.getMoviesListDummy())
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Success(dummyMovies)
            liveData
        }.`when`(moviesUseCase).getNowPlayingMovies()
        val movieLiveData = viewModel.getNowPlaying()
        verify(moviesUseCase).getNowPlayingMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertNotNull(movieLiveData.value?.data)
            assertEquals(dummyMovies.size, movieLiveData.value?.data?.size)
        }
    }

    @Test
    fun getNowPlayingMovies_Error(){
        val dataError = "DATA ERROR"
        doAnswer {
            val liveData = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            liveData.value = Resource.Error(dataError)
            liveData
        }.`when`(moviesUseCase).getNowPlayingMovies()
        val movieLiveData = viewModel.getNowPlaying()
        verify(moviesUseCase).getNowPlayingMovies()
        movieLiveData.observeForTesting{
            Assert.assertTrue(movieLiveData.value is Resource.Error<PagedList<MovieEntity>>)
            assertEquals(dataError, movieLiveData.value?.message)
        }
    }

}