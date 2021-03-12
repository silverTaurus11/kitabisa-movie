package com.project.myapplication.data.source.local

import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.local.room.MoviesCatalogueDao
import com.project.myapplication.data.source.remote.model.BaseDetailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val moviesCatalogueDao: MoviesCatalogueDao) {

    fun getFavoriteMovies() = moviesCatalogueDao.getFavoriteMovies()

    suspend fun insertMovies(movies: BaseDetailResponse.MovieDetailResponse){
        moviesCatalogueDao.insertMovie(
            RoomMovieEntity(movies.id!!, movies.name!!, movies.description!!, movies.rating!!,
                posterPath = movies.posterPath?:"", releaseDate = movies.releaseDate)
        )
    }

    suspend fun updateFavoriteMovie(id: Int, isFavorite: Boolean){
        moviesCatalogueDao.updateFavoriteMovie(id, isFavorite)
    }

    fun getMovieDetail(id: Int) = moviesCatalogueDao.getMovieDetail(id)

}