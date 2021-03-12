package com.project.myapplication.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.local.room.MoviesCatalogueDao

@Database(entities = [RoomMovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesCatalogueDatabase : RoomDatabase() {
    abstract fun moviesCatalogueDao(): MoviesCatalogueDao
}