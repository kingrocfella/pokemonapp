package com.example.mypokemonapplication.pokemonlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.example.mypokemonapplication.common.api.ApiService
import com.example.mypokemonapplication.common.api.makeAPICall

data class PokemonListState(
    var isLoading: Boolean = true,
    var isError: Boolean = false,
    var pokemonList: PokemonList? = null
)

class PokemonListViewModel(
    private val apiService: ApiService = makeAPICall
) : ViewModel() {
    private var _pokemonList by mutableStateOf(PokemonListState())
    val getPokemonListState: PokemonListState
        get() = _pokemonList

    init {
        getPokemonList()
    }

    private fun getPokemonList(limit: Int = 20, offset: Int = 0) {
        try {
            viewModelScope.launch {
                val response = apiService.getPokemonData(limit, offset)
                _pokemonList =
                    _pokemonList.copy(isLoading = false, isError = false, pokemonList = response)
            }
        } catch (_: Exception) {
            _pokemonList = _pokemonList.copy(isLoading = false, isError = true)
        }
    }

    fun getPaginatedPokemonList(isNext: Boolean = false) {
        val url = when (isNext) {
            true -> _pokemonList.pokemonList?.next
            else -> _pokemonList.pokemonList?.previous
        }
        if(url?.isEmpty() == true) return
        _pokemonList = _pokemonList.copy(isLoading = true)
        val uri = url?.toUri()
        val offset = uri?.getQueryParameter("offset")?.toIntOrNull() ?: 0
        val limit = uri?.getQueryParameter("limit")?.toIntOrNull() ?: 20
        getPokemonList(limit, offset)
    }
}