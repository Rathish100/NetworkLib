package com.example.networklib

import android.content.Context
import com.example.networklib.config.ApiConfig
import com.example.networklib.model.ApiRequest
import com.example.networklib.model.ApiResponse
import com.example.networklib.network.ApiClient
import com.example.networklib.network.ApiService
import com.example.networklib.repository.ApiRepository
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow

class ApiLibrary private constructor(
    private val repository: ApiRepository
) {
    fun call(request: ApiRequest): Flow<ApiResponse<JsonElement>> {
        return repository.execute(request)
    }

    companion object {
        @Volatile private var instance: ApiLibrary? = null

        fun init(config: ApiConfig, context: Context): ApiLibrary {
            return instance ?: synchronized(this) {
                val client = ApiClient(config, context)
                val service = client.retrofit.create(ApiService::class.java)
                ApiLibrary(ApiRepository(service)).also { instance = it }
            }
        }

        fun getInstance(): ApiLibrary {
            return instance ?: throw IllegalStateException("ApiLibrary not initialized. Call init() first.")
        }
    }
}