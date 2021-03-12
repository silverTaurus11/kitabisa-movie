package com.project.myapplication.domain.usecase.detail

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import kotlinx.coroutines.flow.Flow

interface IDetailUseCase {
    fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>>
    fun setFavoriteMovie(id: Int, isFavorite: Boolean)
    fun getMovieReviews(id: Int): LiveData<Resource<PagedList<ReviewEntity>>>
}