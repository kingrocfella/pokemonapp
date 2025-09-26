package com.example.mypokemonapplication.pokemondetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokemonapplication.common.api.ApiService
import com.example.mypokemonapplication.common.api.makeAPICall
import com.example.mypokemonapplication.common.utils.Utils
import kotlinx.coroutines.launch

data class PokemonDetailsState(
    var isLoading: Boolean = true,
    var isError: Boolean = false,
    var pokemonDetailsData: PokemonDetails? = null
)

class PokemonDetailsViewModel(
    url: String,
    private val apiService: ApiService = makeAPICall,
    private val utils: Utils = Utils()
) : ViewModel() {
    private val _pokemonUrl = url
    private var _pokemonDetails by mutableStateOf(PokemonDetailsState())
    val getPokemonDetailsState
        get() = _pokemonDetails

    init {
        getPokemonDetails(_pokemonUrl)
    }

    private fun fetchPokemonDetails(id: Int) {
        try {
            viewModelScope.launch {
                val response = apiService.getPokemonDetailsData(id)
                _pokemonDetails = _pokemonDetails.copy(
                    isLoading = false,
                    isError = false,
                    pokemonDetailsData = response
                )
            }
        } catch (_: Exception) {
            _pokemonDetails =
                _pokemonDetails.copy(isLoading = false, isError = true, pokemonDetailsData = null)
        }
    }

    fun getPokemonDetails(pokemonUrl: String) {
        val pokemonId = utils.getPokemonIdFromUrl(pokemonUrl)
        if (pokemonId != null) {
            fetchPokemonDetails(pokemonId)
        } else {
            _pokemonDetails =
                _pokemonDetails.copy(isLoading = false, isError = true, pokemonDetailsData = null)
        }
    }
}