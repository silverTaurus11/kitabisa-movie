package com.project.myapplication.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class GenreEntity(
    @SerializedName("id") val id: Int?= 0,
    @SerializedName("name") val name: String?= null
)