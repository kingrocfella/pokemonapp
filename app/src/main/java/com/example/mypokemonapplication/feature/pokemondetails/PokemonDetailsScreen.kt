package com.example.mypokemonapplication.feature.pokemondetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypokemonapplication.data.models.UISkeletonData
import com.example.mypokemonapplication.data.models.ViewModelState
import com.example.mypokemonapplication.feature.common.UISkeleton

@Composable
fun PokemonDetailsScreen() {
    val pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
    val pokemonDetailsResult by pokemonDetailsViewModel.getPokemonDetailsState.collectAsState()
    val state = pokemonDetailsResult

    UISkeleton(
        UISkeletonData(
            isLoading = state.isLoading,
            isError = state.isError,
            isDataEmpty = state.data?.name?.isEmpty() == true
        ),
        {
            if (state.data != null) {
                DisplayPokemonDetails(state.data)
            }
        }
    )
}

