package com.project.myapplication.di

import com.project.myapplication.data.repository.MovieRepository
import com.project.myapplication.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(moviesRepository: MovieRepository): IMovieRepository

}