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

        private const val DEFAULT_INSTANCE_NAME = "default"

        fun init (context: Context, serviceNames:Map<String, ApiConfig>, name: String = DEFAULT_INSTANCE_NAME): ApiLibrary {
            return synchronized(this) {
                // Create a service for each named config
                val services = serviceNames.mapValues { (_, config) ->
                    ApiClient(config, context)
                        .retrofit
                        .create(ApiService::class.java)
                }
                ApiLibrary(ApiRepository(services)).also { instance = it }
            }
        }

        fun getInstance(name: String = DEFAULT_INSTANCE_NAME): ApiLibrary {
            return instance ?: throw IllegalStateException("ApiLibrary with name '$name' not initialized. Call init() first.")
        }
    }
}