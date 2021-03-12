package com.project.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.util.LiveDataTestUtil.observeForTesting
import com.project.myapplication.domain.usecase.detail.DetailUseCase
import com.project.myapplication.ui.detail.DetailViewModel
import com.project.myapplication.utils.DataDummy
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var detailUseCase: DetailUseCase

    private val dummyId = DataDummy.getMoviesListDummy()[0].id
    private val dummyMovie = DataDummy.getMovie(dummyId)

    @Before
    fun setUp(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailViewModel(detailUseCase)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getMovieDetail_Success() {
        val dummyRoomMovieShow = RoomMovieEntity().apply {
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
        doAnswer {
            flowOf(Resource.Success(dummyRoomMovieShow))
        }.`when`(detailUseCase).getMovieDetail(Mockito.anyInt())
        val movieDetail = viewModel.getMovieDetail(Mockito.anyInt())
        verify(detailUseCase).getMovieDetail(Mockito.anyInt())
        movieDetail.observeForTesting{
            Assert.assertTrue(movieDetail.value is Resource.Success<RoomMovieEntity>)
            val moviesEntities = movieDetail.value?.data
            Assert.assertNotNull(moviesEntities)
            assertEquals(dummyRoomMovieShow.id, moviesEntities?.id)
            assertEquals(dummyRoomMovieShow.name, moviesEntities?.name)
            assertEquals(dummyRoomMovieShow.description, moviesEntities?.description)
            assertEquals(dummyRoomMovieShow.rating, moviesEntities?.rating)
            assertEquals(dummyRoomMovieShow.releaseDate, moviesEntities?.releaseDate)
            assertEquals(dummyRoomMovieShow.duration, moviesEntities?.duration)
            assertEquals(dummyRoomMovieShow.genre, moviesEntities?.genre)
            assertEquals(dummyRoomMovieShow.language, moviesEntities?.language)
            assertEquals(dummyRoomMovieShow.posterPath, moviesEntities?.posterPath)
        }
    }

    @Test
    fun getMovieDetail_Error() {
        val errorMessage = "Data Error"
        doAnswer {
            flowOf(Resource.Error<RoomMovieEntity>(errorMessage))
        }.`when`(detailUseCase).getMovieDetail(Mockito.anyInt())
        val movieDetail = viewModel.getMovieDetail(Mockito.anyInt())
        verify(detailUseCase).getMovieDetail(any())
        movieDetail.observeForTesting{
            Assert.assertTrue(movieDetail.value is Resource.Error<RoomMovieEntity>)
            Assert.assertEquals(errorMessage, movieDetail.value?.message ?: "")
        }
    }
}