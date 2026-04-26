package com.example.networklib.model

sealed class ApiResponse<out T> {
    data class Success<T>(
        val data: T,
        val statusCode: Int
    ) : ApiResponse<T>()

    data class Error(
        val message: String,
        val statusCode: Int? = null,
        val throwable: Throwable? = null
    ) : ApiResponse<Nothing>()

    object Loading : ApiResponse<Nothing>()
}