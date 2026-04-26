package com.example.networklib.model

data class ApiRequest(
    val endpoint: String,
    val method: HttpMethod = HttpMethod.GET,
    val headers: Map<String, String> = emptyMap(),
    val queryParams: Map<String, String> = emptyMap(),
    val body: Any? = null
)

enum class HttpMethod {
    GET, POST, PUT, DELETE, PATCH
}