package com.project.myapplication.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ReviewEntity(
    @SerializedName("id") val id: String?= null,
    @SerializedName("author") val author: String?= null,
    @SerializedName("content") val content: String?= null,
    @SerializedName("created_at") val createdAt: String?= null
)