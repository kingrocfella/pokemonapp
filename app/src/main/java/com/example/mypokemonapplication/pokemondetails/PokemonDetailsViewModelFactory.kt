package com.example.mypokemonapplication.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailsViewModelFactory(private val url: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            PokemonDetailsViewModel::class.java -> PokemonDetailsViewModel(url)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
        } as T
    }
}
