package com.example.mypokemonapplication.feature.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokemonapplication.data.api.ApiService
import com.example.mypokemonapplication.common.utils.Utils
import com.example.mypokemonapplication.data.repository.PokemonUrlRepository
import com.example.mypokemonapplication.data.models.PokemonDetails
import com.example.mypokemonapplication.data.models.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val apiService: ApiService,
    pokemonUrlRepository: PokemonUrlRepository
) : ViewModel() {
    private val _pokemonDetails = MutableStateFlow(ViewModelState<PokemonDetails>())
    val getPokemonDetailsState = _pokemonDetails.asStateFlow()

    init {
        // Listen to URL changes from the repository
        pokemonUrlRepository.selectedPokemonUrl
            .onEach { url ->
                url?.let { getPokemonDetails(it) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchPokemonDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonDetailsData(id)
                _pokemonDetails.value = _pokemonDetails.value.copy(
                    isLoading = false,
                    isError = false,
                    data = response,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _pokemonDetails.value = _pokemonDetails.value.copy(
                    isLoading = false,
                    isError = true,
                    data = null,
                    errorMessage = e.message
                )
            }
        }
    }

    fun getPokemonDetails(pokemonUrl: String) {
        val pokemonId = Utils.getPokemonIdFromUrl(pokemonUrl)
        if (pokemonId != null) {
            fetchPokemonDetails(pokemonId)
        } else {
            _pokemonDetails.value = _pokemonDetails.value.copy(
                isLoading = false,
                isError = true,
                data = null,
                errorMessage = "Invalid Pokemon URL"
            )
        }
    }
}