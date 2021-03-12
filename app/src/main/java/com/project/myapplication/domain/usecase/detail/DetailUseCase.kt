package com.project.myapplication.domain.usecase.detail

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val movieRepository: IMovieRepository): IDetailUseCase {
    override fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>> {
        return movieRepository.getMovieDetail(id)
    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean) {
        movieRepository.setFavoriteMovie(id, isFavorite)
    }

    override fun getMovieReviews(id: Int): LiveData<Resource<PagedList<ReviewEntity>>> {
        return movieRepository.getMovieReviews(id)
    }
}