package com.currency.rateman.data.repository

sealed class ApiCallResult<out T> {
    data object Loading : ApiCallResult<Nothing>()
    data class Success<T>(val data: T) : ApiCallResult<T>()
    data class Error(val exception: Throwable) : ApiCallResult<Nothing>()
}