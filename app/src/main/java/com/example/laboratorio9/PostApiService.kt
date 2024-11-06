package com.example.laboratorio9

import retrofit2.http.*

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon

    @POST("pokemon")
    suspend fun createPokemon(@Body pokemon: Pokemon): Pokemon

    @PUT("pokemon/{name}")
    suspend fun updatePokemon(@Path("name") name: String, @Body pokemon: Pokemon): Pokemon

    @DELETE("pokemon/{name}")
    suspend fun deletePokemon(@Path("name") name: String)
}