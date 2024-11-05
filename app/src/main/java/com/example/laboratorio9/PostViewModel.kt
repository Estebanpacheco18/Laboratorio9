package com.example.laboratorio9

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PokeViewModel : ViewModel() {
    val pokemonList: SnapshotStateList<PokemonResult> = mutableStateListOf()
    var selectedPokemon: Pokemon? by mutableStateOf(null)

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            val response = PokeModule.pokeApiService.getPokemonList()
            pokemonList.addAll(response.results)
        }
    }

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            selectedPokemon = PokeModule.pokeApiService.getPokemonByName(name)
        }
    }
}