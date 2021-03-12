package com.project.myapplication.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.project.myapplication.data.source.local.room.MoviesCatalogueDao
import com.project.myapplication.data.source.local.room.MoviesCatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesCatalogueDatabase = databaseBuilder(
        context,
        MoviesCatalogueDatabase::class.java, "MovieCatalogue.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieCatalogue(database: MoviesCatalogueDatabase): MoviesCatalogueDao =
        database.moviesCatalogueDao()
}