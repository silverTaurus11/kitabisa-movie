package com.project.myapplication.di

import com.project.myapplication.domain.usecase.detail.DetailUseCase
import com.project.myapplication.domain.usecase.detail.IDetailUseCase
import com.project.myapplication.domain.usecase.favorite.FavoriteUseCase
import com.project.myapplication.domain.usecase.favorite.IFavoriteUseCase
import com.project.myapplication.domain.usecase.main.IMovieUseCase
import com.project.myapplication.domain.usecase.main.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideMoviesUseCase(movieUseCase: MovieUseCase): IMovieUseCase

    @Binds
    abstract fun provideDetailUseCase(detailUseCase: DetailUseCase): IDetailUseCase

    @Binds
    abstract fun provideFavoriteUseCase(favoriteUseCase: FavoriteUseCase): IFavoriteUseCase

}