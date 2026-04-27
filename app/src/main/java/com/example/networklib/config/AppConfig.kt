package com.example.networklib.config

data class ApiConfig(
    val baseUrl: String,
    val apiKey: String? = null,
    val authToken: String? = null,
    val connectTimeoutSeconds: Long = 30,
    val readTimeoutSeconds: Long = 30,
    val retryCount: Int = 3,
    val cacheEnabled: Boolean = true,
    val cacheSizeMb: Long = 10L,
    val isDebug: Boolean = false,
    val trustAllCertificates: Boolean = false
)
