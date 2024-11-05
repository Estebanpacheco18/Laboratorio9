package com.example.laboratorio9

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon
}