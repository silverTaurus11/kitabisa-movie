package com.project.myapplication.ui.favorite

import androidx.lifecycle.ViewModel
import com.project.myapplication.domain.usecase.favorite.IFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteUseCase: IFavoriteUseCase): ViewModel() {

    fun getFavoriteMovies() = favoriteUseCase.getFavoriteMovies()
}