package com.project.myapplication.data.source.remote.setting

import com.project.myapplication.data.source.remote.model.BaseDetailResponse
import com.project.myapplication.data.source.remote.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): BaseResponse.MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): BaseResponse.MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): BaseResponse.MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): BaseResponse.MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")movieId: Int,
        @Query("api_key") apiKey: String): BaseDetailResponse.MovieDetailResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): BaseResponse.ReviewResponse

}