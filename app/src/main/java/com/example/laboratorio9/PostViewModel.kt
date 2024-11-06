package com.example.laboratorio9

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonResult>>(emptyList())
    val pokemonList: StateFlow<List<PokemonResult>> = _pokemonList

    private val _selectedPokemon = MutableStateFlow<Pokemon?>(null)
    val selectedPokemon: StateFlow<Pokemon?> = _selectedPokemon

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            val response = PokeModule.pokeApiService.getPokemonList()
            _pokemonList.value = response.results
        }
    }

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            val pokemon = PokeModule.pokeApiService.getPokemonByName(name)
            _selectedPokemon.value = pokemon
        }
    }

    fun createPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            val newPokemon = PokeModule.pokeApiService.createPokemon(pokemon)
            _pokemonList.value = _pokemonList.value + PokemonResult(newPokemon.name, "")
        }
    }

    fun updatePokemon(name: String, pokemon: Pokemon) {
        viewModelScope.launch {
            val updatedPokemon = PokeModule.pokeApiService.updatePokemon(name, pokemon)
            _pokemonList.value = _pokemonList.value.map { if (it.name == name) PokemonResult(updatedPokemon.name, "") else it }
        }
    }

    fun deletePokemon(name: String) {
        viewModelScope.launch {
            PokeModule.pokeApiService.deletePokemon(name)
            _pokemonList.value = _pokemonList.value.filter { it.name != name }
        }
    }
}