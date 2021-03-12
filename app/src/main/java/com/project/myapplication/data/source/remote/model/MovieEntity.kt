package com.project.myapplication.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class MovieEntity(
    @SerializedName("id") val id: Int?= 0,
    @SerializedName("title") val name: String?= null,
    @SerializedName("overview") val description: String?= null,
    @SerializedName("vote_average")val rating: Float?= 0F,
    @SerializedName("poster_path") val posterPath: String?= null,
    @SerializedName("release_date") val releaseDate: String?= null
)