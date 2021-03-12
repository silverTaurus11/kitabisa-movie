package com.project.myapplication.domain.usecase.favorite

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.domain.repository.IMovieRepository
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(private val movieRepository: IMovieRepository): IFavoriteUseCase {
    override fun getFavoriteMovies(): LiveData<PagedList<RoomMovieEntity>> {
        return movieRepository.getFavoriteMovies()
    }
}