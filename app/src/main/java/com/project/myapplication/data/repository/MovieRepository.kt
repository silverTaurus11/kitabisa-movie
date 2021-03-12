package com.project.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.myapplication.data.AppExecutors
import com.project.myapplication.data.NetworkBoundResource
import com.project.myapplication.data.Resource
import com.project.myapplication.data.paging.NetworkPagedListResourcesLiveData
import com.project.myapplication.data.source.local.LocalDataSource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.RemoteDataSource
import com.project.myapplication.data.source.remote.model.BaseDetailResponse
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.data.source.remote.setting.ApiResponse
import com.project.myapplication.domain.repository.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {
    private val perPageSize = 20

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkPagedListResourcesLiveData<MovieEntity, List<MovieEntity>>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getPopularMovies(currentPage)
            }

            override fun convertToListType(remoteData: List<MovieEntity>): List<MovieEntity> {
                return remoteData
            }

            override fun getPageSize(): Int = perPageSize/2

            override fun isLastPage(remoteData: List<MovieEntity>): Boolean {
                return remoteData.size < perPageSize
            }

        }.asLiveData()
    }

    override fun getTopRatedMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkPagedListResourcesLiveData<MovieEntity, List<MovieEntity>>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getTopRatedMovies(currentPage)
            }

            override fun convertToListType(remoteData: List<MovieEntity>): List<MovieEntity> {
                return remoteData
            }

            override fun getPageSize(): Int = perPageSize/2

            override fun isLastPage(remoteData: List<MovieEntity>): Boolean {
                return remoteData.size < perPageSize
            }

        }.asLiveData()
    }

    override fun getUpComingMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkPagedListResourcesLiveData<MovieEntity, List<MovieEntity>>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getUpComingMovies(currentPage)
            }

            override fun convertToListType(remoteData: List<MovieEntity>): List<MovieEntity> {
                return remoteData
            }

            override fun getPageSize(): Int = perPageSize/2

            override fun isLastPage(remoteData: List<MovieEntity>): Boolean {
                return remoteData.size < perPageSize
            }

        }.asLiveData()
    }

    override fun getNowPlayingMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkPagedListResourcesLiveData<MovieEntity, List<MovieEntity>>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getNowPlayingMovies(currentPage)
            }

            override fun convertToListType(remoteData: List<MovieEntity>): List<MovieEntity> {
                return remoteData
            }

            override fun getPageSize(): Int = perPageSize/2

            override fun isLastPage(remoteData: List<MovieEntity>): Boolean {
                return remoteData.size < perPageSize
            }

        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): Flow<Resource<RoomMovieEntity>> {
        return object : NetworkBoundResource<RoomMovieEntity, BaseDetailResponse.MovieDetailResponse>(){
            override fun shouldGetFromDataBaseWhenErrorResponse(data: RoomMovieEntity?): Boolean {
                return data != null
            }

            override fun loadFromDB(): Flow<RoomMovieEntity> {
                return localDataSource.getMovieDetail(id)
            }

            override fun shouldFetch(data: RoomMovieEntity?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<BaseDetailResponse.MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(id)
            }

            override suspend fun saveCallResult(data: BaseDetailResponse.MovieDetailResponse) {
                localDataSource.insertMovies(data)
            }

        }.asFlow()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<RoomMovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(perPageSize)
            .setPageSize(perPageSize)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config)
            .setFetchExecutor(appExecutors.diskIO()).build()

    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean) {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                localDataSource.updateFavoriteMovie(id, isFavorite)
            }
        }
    }

    override fun getMovieReviews(id: Int): LiveData<Resource<PagedList<ReviewEntity>>> {
        return object : NetworkPagedListResourcesLiveData<ReviewEntity, List<ReviewEntity>>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<List<ReviewEntity>>> {
                return remoteDataSource.getMovieReviews(id, currentPage)
            }

            override fun convertToListType(remoteData: List<ReviewEntity>): List<ReviewEntity> {
                return remoteData
            }

            override fun getPageSize(): Int = perPageSize/2

            override fun isLastPage(remoteData: List<ReviewEntity>): Boolean {
                return remoteData.size < perPageSize
            }

        }.asLiveData()
    }

}