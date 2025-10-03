package com.example.mypokemonapplication.feature.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.example.mypokemonapplication.data.api.ApiService
import com.example.mypokemonapplication.common.screens.ScreenNames
import com.example.mypokemonapplication.data.repository.PokemonUrlRepository
import com.example.mypokemonapplication.services.NavigationService
import com.example.mypokemonapplication.data.models.PokemonList
import com.example.mypokemonapplication.data.models.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val apiService: ApiService,
    private val pokemonUrlRepository: PokemonUrlRepository,
    private val navigationService: NavigationService
) : ViewModel() {
    private val _pokemonList = MutableStateFlow(ViewModelState<PokemonList>())
    val getPokemonListState: StateFlow<ViewModelState<PokemonList>> = _pokemonList.asStateFlow()

    init {
        getPokemonList()
    }

    fun setSelectedPokemonUrl(url: String) {
        pokemonUrlRepository.setSelectedPokemonUrl(url)
    }
    
    fun navigateToDetails() {
        navigationService.navigateTo(ScreenNames.DETAILS)
    }

    fun getPaginatedPokemonList(isNext: Boolean = false) {
        val currentState = _pokemonList.value
        val url = when (isNext) {
            true -> currentState.data?.next
            else -> currentState.data?.previous
        }
        if (url?.isEmpty() == true) return
        _pokemonList.value = _pokemonList.value.copy(isLoading = true)
        val uri = url?.toUri()
        val offset = uri?.getQueryParameter("offset")?.toIntOrNull() ?: 0
        val limit = uri?.getQueryParameter("limit")?.toIntOrNull() ?: 20
        getPokemonList(limit, offset)
    }

    private fun getPokemonList(limit: Int = 20, offset: Int = 0) {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonData(limit, offset)
                _pokemonList.value = _pokemonList.value.copy(
                    isLoading = false,
                    isError = false,
                    data = response,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _pokemonList.value = _pokemonList.value.copy(
                    isLoading = false,
                    isError = true,
                    data = null,
                    errorMessage = e.message
                )
            }
        }
    }
}