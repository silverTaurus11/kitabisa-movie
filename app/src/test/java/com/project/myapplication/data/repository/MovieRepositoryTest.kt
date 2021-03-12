package com.project.myapplication.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.project.myapplication.data.AppExecutors
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.LocalDataSource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.RemoteDataSource
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.data.source.remote.setting.ApiResponse
import com.project.myapplication.util.LiveDataTestUtil
import com.project.myapplication.util.LiveDataTestUtil.observeForTesting
import com.project.myapplication.util.PagedListUtil
import com.project.myapplication.utils.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class MovieRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val waiter = CountDownLatch(1)
    private val moviesRepository = MovieRepository(remote, local, appExecutors)

    private val moviesResponses = DataDummy.getMoviesListDummy()
    private val movieId = moviesResponses[0].id

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getMovieDetailTest_Success() = runBlocking{
        val dummyMovie = DataDummy.getMovie(movieId)
        val dummyRoomMovie = RoomMovieEntity().apply {
            id = dummyMovie.id
            name = dummyMovie.name
            description = dummyMovie.description
            rating = dummyMovie.rating
            releaseDate = dummyMovie.releaseDate
            duration = dummyMovie.duration
            genre = dummyMovie.genre?.map { it.name }?.joinToString(", ")
            language = dummyMovie.language
            posterPath = dummyMovie.posterPath
        }
        Mockito.`when`(local.getMovieDetail(Mockito.anyInt())).thenReturn(flowOf(dummyRoomMovie))
        Mockito.`when`(remote.getMovieDetail(Mockito.anyInt())).thenReturn(flowOf(ApiResponse.Success(dummyMovie)))
        val moviesLiveData = moviesRepository.getMovieDetail(movieId?:0).asLiveData()
        val moviesLiveDataValue = LiveDataTestUtil.getValue(moviesLiveData)
        verify(local, Mockito.times(2)).getMovieDetail(Mockito.anyInt())
        Assert.assertTrue(moviesLiveDataValue is Resource.Loading<RoomMovieEntity>)
        moviesLiveData.observeForTesting {
            Assert.assertTrue(moviesLiveData.value is Resource.Success<RoomMovieEntity>)
        }
        val movieEntity = moviesLiveData.value?.data
        Assert.assertNotNull(movieEntity)
        assertEquals(dummyRoomMovie.id, movieEntity?.id)
        assertEquals(dummyRoomMovie.name, movieEntity?.name)
        assertEquals(dummyRoomMovie.description, movieEntity?.description)
        assertEquals(dummyRoomMovie.rating, movieEntity?.rating)
        assertEquals(dummyRoomMovie.releaseDate, movieEntity?.releaseDate)
        assertEquals(dummyRoomMovie.duration, movieEntity?.duration)
        assertEquals(dummyRoomMovie.genre, movieEntity?.genre)
        assertEquals(dummyRoomMovie.language, movieEntity?.language)
        assertEquals(dummyRoomMovie.posterPath, movieEntity?.posterPath)
    }

    @Test
    fun getMovieDetailTest_Error() = runBlocking{
        val errorMessage = "Data Error"
        doAnswer {
            flowOf(null)
        }.`when`(local).getMovieDetail(Mockito.anyInt())
        doAnswer {
            flowOf(ApiResponse.Error(errorMessage))
        }.`when`(remote).getMovieDetail(Mockito.anyInt())
        val moviesLiveData = moviesRepository.getMovieDetail(movieId?:0).asLiveData()
        val moviesLiveDataValue = LiveDataTestUtil.getValue(moviesLiveData)
        verify(remote).getMovieDetail(Mockito.anyInt())
        Assert.assertTrue(moviesLiveDataValue is Resource.Loading<RoomMovieEntity>)
        moviesLiveData.observeForTesting {
            Assert.assertTrue(moviesLiveData.value is Resource.Error<RoomMovieEntity>)
            Assert.assertEquals(errorMessage, moviesLiveData.value?.message?:"")
        }
    }

    @Test
    fun getFavoriteMovieTest(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, RoomMovieEntity>
        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        val favoriteMovies = Resource.Success(PagedListUtil.mockPagedList(moviesResponses))
        moviesRepository.getFavoriteMovies()
        verify(local).getFavoriteMovies()
        Assert.assertNotNull(favoriteMovies)
        assertEquals(moviesResponses.size, favoriteMovies.data?.size?:0)
    }

    @Test
    fun getPopularMovies_Test() {
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Success(moviesResponses)
            data
        }.`when`(remote).getPopularMovies(Mockito.anyInt())
        val popularMovie = moviesRepository.getPopularMovies()
        verify(remote).getPopularMovies(Mockito.anyInt())
        Assert.assertTrue(popularMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        popularMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(popularMovie.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertEquals(moviesResponses.size, popularMovie.value?.data?.size?:0)
        }
    }

    @Test
    fun getPopularMoviesError_Test() {
        val dataError = "DATA ERROR"
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Error(dataError)
            data
        }.`when`(remote).getPopularMovies(Mockito.anyInt())
        val popularMovie = moviesRepository.getPopularMovies()
        verify(remote).getPopularMovies(Mockito.anyInt())
        Assert.assertTrue(popularMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        popularMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(popularMovie.value is Resource.Error<PagedList<MovieEntity>>)
            Assert.assertEquals(dataError, popularMovie.value?.message)
        }
    }

    @Test
    fun getTopRatedMovies_Test() {
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Success(moviesResponses)
            data
        }.`when`(remote).getTopRatedMovies(Mockito.anyInt())
        val topRatedMovie = moviesRepository.getTopRatedMovies()
        verify(remote).getTopRatedMovies(Mockito.anyInt())
        Assert.assertTrue(topRatedMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        topRatedMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(topRatedMovie.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertEquals(moviesResponses.size, topRatedMovie.value?.data?.size?:0)
        }
    }

    @Test
    fun getTopRatedMoviesError_Test() {
        val dataError = "DATA ERROR"
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Error(dataError)
            data
        }.`when`(remote).getTopRatedMovies(Mockito.anyInt())
        val topRatedMovie = moviesRepository.getTopRatedMovies()
        verify(remote).getTopRatedMovies(Mockito.anyInt())
        Assert.assertTrue(topRatedMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        topRatedMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(topRatedMovie.value is Resource.Error<PagedList<MovieEntity>>)
            Assert.assertEquals(dataError, topRatedMovie.value?.message)
        }
    }

    @Test
    fun getUpComingMovies_Test() {
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Success(moviesResponses)
            data
        }.`when`(remote).getUpComingMovies(Mockito.anyInt())
        val upComingMovie = moviesRepository.getUpComingMovies()
        verify(remote).getUpComingMovies(Mockito.anyInt())
        Assert.assertTrue(upComingMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        upComingMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(upComingMovie.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertEquals(moviesResponses.size, upComingMovie.value?.data?.size?:0)
        }
    }

    @Test
    fun getUpComingMoviesError_Test() {
        val dataError = "DATA ERROR"
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Error(dataError)
            data
        }.`when`(remote).getUpComingMovies(Mockito.anyInt())
        val upComingMovie = moviesRepository.getUpComingMovies()
        verify(remote).getUpComingMovies(Mockito.anyInt())
        Assert.assertTrue(upComingMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        upComingMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(upComingMovie.value is Resource.Error<PagedList<MovieEntity>>)
            Assert.assertEquals(dataError, upComingMovie.value?.message)
        }
    }

    @Test
    fun getNowPlayingMovies_Test() {
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Success(moviesResponses)
            data
        }.`when`(remote).getNowPlayingMovies(Mockito.anyInt())
        val nowPlayingMovie = moviesRepository.getNowPlayingMovies()
        verify(remote).getNowPlayingMovies(Mockito.anyInt())
        Assert.assertTrue(nowPlayingMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        nowPlayingMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(nowPlayingMovie.value is Resource.Success<PagedList<MovieEntity>>)
            Assert.assertEquals(moviesResponses.size, nowPlayingMovie.value?.data?.size?:0)
        }
    }

    @Test
    fun getNowPlayingMoviesError_Test() {
        val dataError = "DATA ERROR"
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<MovieEntity>>>()
            data.value = ApiResponse.Error(dataError)
            data
        }.`when`(remote).getNowPlayingMovies(Mockito.anyInt())
        val nowPlayingMovie = moviesRepository.getNowPlayingMovies()
        verify(remote).getNowPlayingMovies(Mockito.anyInt())
        Assert.assertTrue(nowPlayingMovie.value is Resource.Loading<PagedList<MovieEntity>>)
        nowPlayingMovie.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(nowPlayingMovie.value is Resource.Error<PagedList<MovieEntity>>)
            Assert.assertEquals(dataError, nowPlayingMovie.value?.message)
        }
    }

    @Test
    fun getMovieReviews_Success() {
        val reviewList = DataDummy.getMovieReviewList()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<ReviewEntity>>>()
            data.value = ApiResponse.Success(reviewList)
            data
        }.`when`(remote).getMovieReviews(Mockito.anyInt(), Mockito.anyInt())
        val movieReview = moviesRepository.getMovieReviews(1)
        verify(remote).getMovieReviews(Mockito.anyInt(), Mockito.anyInt())
        Assert.assertTrue(movieReview.value is Resource.Loading<PagedList<ReviewEntity>>)
        movieReview.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(movieReview.value is Resource.Success<PagedList<ReviewEntity>>)
        }
    }

    @Test
    fun getMovieReviews_Error() {
        val dataError = "DATA ERROR"
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).diskIO()
        doAnswer {
            Executors.newSingleThreadExecutor()
        }.`when`(appExecutors).mainThread()
        doAnswer {
            val data = MutableLiveData<ApiResponse<List<ReviewEntity>>>()
            data.value = ApiResponse.Error(dataError)
            data
        }.`when`(remote).getMovieReviews(Mockito.anyInt(), Mockito.anyInt())
        val reviewLiveData = moviesRepository.getMovieReviews(1)
        verify(remote).getMovieReviews(Mockito.anyInt(), Mockito.anyInt())
        Assert.assertTrue(reviewLiveData.value is Resource.Loading<PagedList<ReviewEntity>>)
        reviewLiveData.observeForTesting {
            waiter.await(2, TimeUnit.SECONDS)
            Assert.assertTrue(reviewLiveData.value is Resource.Error<PagedList<ReviewEntity>>)
            Assert.assertEquals(dataError, reviewLiveData.value?.message)
        }
    }

}