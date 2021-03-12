package com.project.myapplication.domain.usecase.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.source.local.model.RoomMovieEntity

interface IFavoriteUseCase {
    fun getFavoriteMovies(): LiveData<PagedList<RoomMovieEntity>>
}