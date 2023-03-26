package com.example.githubrepowatcher.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL = "https://api.github.com/"

    private val contentType = MediaType.get("application/json")
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val kotlinxConverterFactory = json.asConverterFactory(contentType)

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(kotlinxConverterFactory)
        .baseUrl(BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}