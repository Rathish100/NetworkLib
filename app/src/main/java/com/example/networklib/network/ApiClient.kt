package com.example.networklib.network

import android.content.Context
import com.example.networklib.config.ApiConfig
import com.example.networklib.network.interceptor.AuthInterceptor
import com.example.networklib.network.interceptor.RetryInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.cache.CacheInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(private val config: ApiConfig, context: Context) {

    private val cache = if (config.cacheEnabled) {
        Cache(context.cacheDir, config.cacheSizeMb * 1024 * 1024)
    } else null

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(AuthInterceptor(config))
        .addInterceptor(RetryInterceptor(config.retryCount))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (config.isDebug) HttpLoggingInterceptor.Level.BODY else TODO()
        })
        .connectTimeout(config.connectTimeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(config.readTimeoutSeconds, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(config.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}