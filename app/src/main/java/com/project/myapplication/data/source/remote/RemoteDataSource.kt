package com.project.myapplication.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.project.myapplication.data.source.remote.model.BaseDetailResponse
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.data.source.remote.setting.ApiResponse
import com.project.myapplication.data.source.remote.setting.ApiService
import com.project.myapplication.data.source.remote.setting.ServiceConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    private val key: String = ServiceConst.API_KEY

    fun getPopularMovies(page: Int = 1): LiveData<ApiResponse<List<MovieEntity>>> {
        return flow {
            try {
                val response = apiService.getPopularMovies(key, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    fun getTopRatedMovies(page: Int = 1): LiveData<ApiResponse<List<MovieEntity>>> {
        return flow {
            try {
                val response = apiService.getTopRatedMovies(key, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    fun getUpComingMovies(page: Int = 1): LiveData<ApiResponse<List<MovieEntity>>> {
        return flow {
            try {
                val response = apiService.getUpComingMovies(key, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    fun getNowPlayingMovies(page: Int = 1): LiveData<ApiResponse<List<MovieEntity>>> {
        return flow {
            try {
                val response = apiService.getNowPlayingMovies(key, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<BaseDetailResponse.MovieDetailResponse>> {
        return flow {
            try {
                val response = apiService.getMovieDetail(id, key)
                val statusCode = response.statusCode
                if (statusCode != null){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieReviews(id: Int, page: Int = 1): LiveData<ApiResponse<List<ReviewEntity>>> {
        return flow {
            try {
                val response = apiService.getMovieReviews(id, key, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }

}