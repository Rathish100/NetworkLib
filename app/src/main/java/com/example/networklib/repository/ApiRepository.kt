package com.example.networklib.repository

import com.example.networklib.model.ApiRequest
import com.example.networklib.model.ApiResponse
import com.example.networklib.model.HttpMethod
import com.example.networklib.network.ApiService
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiRepository(private val service: ApiService) {

    fun execute(request: ApiRequest): Flow<ApiResponse<JsonElement>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = when (request.method) {
                HttpMethod.GET -> service.get(request.endpoint, request.queryParams, request.headers)
                HttpMethod.POST -> service.post(request.endpoint, request.body ?: {}, request.headers)
                HttpMethod.PUT -> service.put(request.endpoint, request.body ?: {}, request.headers)
                HttpMethod.DELETE -> service.delete(request.endpoint, request.headers)
                HttpMethod.PATCH -> service.patch(request.endpoint, request.body ?: {}, request.headers)
            }

            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body()!!, response.code()))
            } else {
                emit(ApiResponse.Error("HTTP ${response.code()}: ${response.message()}", response.code()))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message ?: "Unknown error", throwable = e))
        }
    }.flowOn(Dispatchers.IO)
}