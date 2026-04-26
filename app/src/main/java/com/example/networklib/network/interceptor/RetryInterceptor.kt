package com.example.networklib.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException


class RetryInterceptor(private val maxRetries: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response? = null
        var lastException: IOException? = null

        while (attempt <= maxRetries) {
            try {
                response = chain.proceed(chain.request())
                if (response.isSuccessful) return response
            } catch (e: IOException) {
                lastException = e
            }
            attempt++
            if (attempt <= maxRetries) {
                Thread.sleep(1000L * attempt) // exponential backoff
            }
        }

        return response ?: throw lastException ?: IOException("Request failed after $maxRetries retries")
    }
}