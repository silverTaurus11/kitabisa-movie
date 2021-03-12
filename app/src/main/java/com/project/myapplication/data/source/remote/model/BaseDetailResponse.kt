package com.project.myapplication.data.source.remote.model

import com.google.gson.annotations.SerializedName

sealed class BaseDetailResponse(
    @SerializedName("status_message") val statusMessage: String?= null,
    @SerializedName("success") val status: Boolean?= false,
    @SerializedName("status_code") val statusCode: Int?= 0) {

    class MovieDetailResponse(
        @SerializedName("id") val id: Int?= 0,
        @SerializedName("title") val name: String?= null,
        @SerializedName("overview") val description: String?= null,
        @SerializedName("vote_average")val rating: Float?= 0F,
        @SerializedName("genres") val genre: List<GenreEntity> ?= null,
        @SerializedName("original_language") val language: String ?= null,
        @SerializedName("release_date") val releaseDate: String ?= null,
        @SerializedName("runtime") val duration: Int ?= 0,
        @SerializedName("poster_path") val posterPath: String?= null): BaseDetailResponse(){
            var isFavorite: Boolean = false
    }
}