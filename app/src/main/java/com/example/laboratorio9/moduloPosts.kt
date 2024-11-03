package com.example.laboratorio9

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val postApiService: PostApiService = retrofit.create(PostApiService::class.java)
}