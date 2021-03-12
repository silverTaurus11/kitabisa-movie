package com.project.myapplication.data

import android.view.View

sealed class Resource<T>(val data: T? = null,
                         val message: String? = null,
                         val isLoadMoreBehaviour: Boolean = false) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null, isLoadMoreBehaviour: Boolean = false)
        : Resource<T>(data, isLoadMoreBehaviour = isLoadMoreBehaviour)
    class Error<T>(message: String,
                   data: T? = null,
                   isLoadMoreError: Boolean = false,
                   val retry: View.OnClickListener?= null) : Resource<T>(data, message, isLoadMoreError)
    class Empty<T>(isLoadMoreBehaviour: Boolean = false): Resource<T>(isLoadMoreBehaviour = isLoadMoreBehaviour)

}

