package com.project.myapplication.data.source.remote.model

import com.google.gson.annotations.SerializedName

sealed class BaseResponse<T>(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val results: List<T> = listOf()){
    class MovieResponse : BaseResponse<MovieEntity>()
    class ReviewResponse : BaseResponse<ReviewEntity>()
}