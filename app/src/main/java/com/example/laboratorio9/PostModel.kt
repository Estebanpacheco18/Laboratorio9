package com.example.laboratorio9

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonResult>
)

data class PokemonResult(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("sprites") val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)