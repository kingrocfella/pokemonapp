package com.example.mypokemonapplication.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonUrlRepository @Inject constructor() {
    private val _selectedPokemonUrl = MutableStateFlow<String?>(null)
    val selectedPokemonUrl: StateFlow<String?> = _selectedPokemonUrl.asStateFlow()

    fun setSelectedPokemonUrl(url: String) {
        _selectedPokemonUrl.value = url
    }
}
