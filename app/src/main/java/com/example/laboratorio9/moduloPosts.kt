package com.example.laboratorio9

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokeModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokeApiService: PokeApiService = retrofit.create(PokeApiService::class.java)
}