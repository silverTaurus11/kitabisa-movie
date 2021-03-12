package com.project.myapplication.ui.home

import androidx.lifecycle.ViewModel
import com.project.myapplication.domain.usecase.main.IMovieUseCase
import com.project.myapplication.utils.RefreshableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieUseCase: IMovieUseCase): ViewModel() {

    fun getPopularMovies() = RefreshableLiveData{ movieUseCase.getPopularMovies()}

    fun getTopRatedMovies() = RefreshableLiveData{ movieUseCase.getTopRatedMovies()}

    fun getUpComingMovies() = RefreshableLiveData{ movieUseCase.getUpComingMovies()}

    fun getNowPlaying() = RefreshableLiveData{ movieUseCase.getNowPlayingMovies()}
}