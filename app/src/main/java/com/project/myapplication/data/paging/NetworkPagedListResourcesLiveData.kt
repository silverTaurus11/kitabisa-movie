package com.project.myapplication.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.project.myapplication.data.AppExecutors
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.remote.setting.ApiResponse

abstract class NetworkPagedListResourcesLiveData<ResultType, RequestType>(private val mExecutors: AppExecutors){
    private var isLastPage = false
    private val result = MediatorLiveData<Resource<PagedList<ResultType>>>()
    private val dataSource = NetworkDictionaryDataFactory(object : NetworkDictionaryDataSource.DictionaryListener<ResultType>{
        override fun doCallBackLoadAfter(currentPage: Int,
                                         nextPage: Int,
                                         params: PageKeyedDataSource.LoadParams<Int>,
                                         callback: PageKeyedDataSource.LoadCallback<Int, ResultType>) {
            mExecutors.mainThread().execute {
                if(!isLastPage) {
                    fetchFromNetwork(currentPage, nextPage, params, callback, true)
                }
            }
        }
    })

    init {
        fetchFromNetwork(1, 2, null, null)
    }

    protected abstract fun createCall(currentPage: Int = 1): LiveData<ApiResponse<RequestType>>

    protected abstract fun convertToListType(remoteData: RequestType): List<ResultType>

    protected abstract fun getPageSize(): Int

    protected abstract fun isLastPage(remoteData: RequestType): Boolean

    private fun fetchFromNetwork(currentPage: Int, nextPage: Int,
                                 params: PageKeyedDataSource.LoadParams<Int>?,
                                 callbackAfter: PageKeyedDataSource.LoadCallback<Int, ResultType>?,
                                 isLoadMore: Boolean = false) {
        val apiResponse = createCall(currentPage)
        result.value = Resource.Loading(isLoadMoreBehaviour = isLoadMore)
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiResponse.Success -> mExecutors.diskIO().execute {
                    mExecutors.mainThread().execute {
                        val data = convertToListType(response.data)
                        dataSource.updateData(data.toMutableList())
                        callbackAfter?.onResult(data, nextPage)
                        result.addSource(getPagedListLiveData()) { newData ->
                            result.value = Resource.Success(newData)
                        }
                        isLastPage = isLastPage(response.data)
                    }
                }

                is ApiResponse.Empty -> mExecutors.mainThread().execute {
                    result.value = Resource.Empty(isLoadMore)
                }

                is ApiResponse.Error -> {
                    mExecutors.mainThread().execute {
                        result.value = Resource.Error(
                                message =  response.errorMessage,
                                isLoadMoreError = isLoadMore,
                                retry = {
                                    if(params != null && callbackAfter != null){
                                        dataSource.getNetworkDataSource().retry(params, callbackAfter)
                                    }
                                })
                    }
                }
            }
        }
    }

    private fun getPagedListLiveData(): LiveData<PagedList<ResultType>> {
        return LivePagedListBuilder(dataSource, PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(getPageSize())
                .setPageSize(getPageSize())
                .build())
                .setFetchExecutor(mExecutors.diskIO())
                .build()
    }

    fun asLiveData(): LiveData<Resource<PagedList<ResultType>>> {
        return result
    }

}