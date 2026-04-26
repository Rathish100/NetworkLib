package com.example.networklib.network.interceptor

import com.example.networklib.config.ApiConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val config: ApiConfig) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()

        config.apiKey?.let {
            builder.addHeader("x-api-key", it)
        }
        config.authToken?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(builder.build())
    }
}