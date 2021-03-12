package com.project.myapplication.domain.usecase.main

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getTopRatedMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getUpComingMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>>
}