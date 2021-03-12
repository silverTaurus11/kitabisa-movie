package com.project.myapplication.domain.usecase.main

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.domain.repository.IMovieRepository
import com.project.myapplication.domain.usecase.main.IMovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: IMovieRepository): IMovieUseCase {
    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return movieRepository.getPopularMovies()
    }

    override fun getTopRatedMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return movieRepository.getTopRatedMovies()
    }

    override fun getUpComingMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return movieRepository.getUpComingMovies()
    }

    override fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return movieRepository.getNowPlayingMovies()
    }

}