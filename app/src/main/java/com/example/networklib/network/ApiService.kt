package com.example.networklib.network

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap queryParams: Map<String, String> = emptyMap(),
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<JsonElement>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<JsonElement>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<JsonElement>

    @DELETE
    suspend fun delete(
        @Url url: String,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<JsonElement>

    @PATCH
    suspend fun patch(
        @Url url: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, String> = emptyMap()
    ): Response<JsonElement>
}