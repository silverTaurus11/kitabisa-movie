package com.project.myapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.myapplication.domain.usecase.detail.IDetailUseCase
import com.project.myapplication.utils.RefreshableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailUseCase: IDetailUseCase): ViewModel() {

    fun getMovieDetail(id: Int) = RefreshableLiveData{
        detailUseCase.getMovieDetail(id).asLiveData()
    }

    fun updateFavoriteStatus(id: Int, isFavorite: Boolean){
        detailUseCase.setFavoriteMovie(id, isFavorite)
    }

    fun getMovieReviews(id: Int) = RefreshableLiveData{
        detailUseCase.getMovieReviews(id)
    }
}