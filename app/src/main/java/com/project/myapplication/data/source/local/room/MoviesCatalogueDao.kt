package com.project.myapplication.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesCatalogueDao {

    @Query("SELECT * FROM movies where isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, RoomMovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: RoomMovieEntity)

    @Query("SELECT * FROM movies WHERE id =:id")
    fun getMovieDetail(id: Int): Flow<RoomMovieEntity>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavoriteMovie(movieId: Int, isFavorite: Boolean)

}