package com.example.networklib.model

data class ApiRequest(
    val serviceName: String,
    val endpoint: String,
    val method: HttpMethod = HttpMethod.GET,
    val headers: Map<String, String> = emptyMap(),
    val queryParams: Map<String, String> = emptyMap(),
    val body: Any? = null
) {
    /*companion object {
        fun get(
            endpoint: String,
            headers: Map<String, String> = emptyMap(),
            queryParams: Map<String, String> = emptyMap()
        ) = ApiRequest(endpoint, HttpMethod.GET, headers, queryParams)

        fun post(
            endpoint: String,
            body: Any? = null,
            headers: Map<String, String> = emptyMap(),
            queryParams: Map<String, String> = emptyMap()
        ) = ApiRequest(endpoint, HttpMethod.POST, headers, queryParams, body)
    }*/
}

enum class HttpMethod {
    GET, POST, PUT, DELETE, PATCH
}