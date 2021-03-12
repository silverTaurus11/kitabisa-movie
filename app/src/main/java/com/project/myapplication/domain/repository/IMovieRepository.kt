package com.project.myapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getTopRatedMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getUpComingMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>>
    fun getFavoriteMovies(): LiveData<PagedList<RoomMovieEntity>>
    fun setFavoriteMovie(id: Int, isFavorite: Boolean)
    fun getMovieReviews(id: Int): LiveData<Resource<PagedList<ReviewEntity>>>
}