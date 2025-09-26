package com.example.mypokemonapplication.pokemondetails

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypokemonapplication.common.ui.UISkeleton
import com.example.mypokemonapplication.common.ui.UISkeletonData

@Composable
fun PokemonDetailsScreen(detailUrl: String) {
    val pokemonDetailsViewModel = viewModel<PokemonDetailsViewModel>(
        factory = PokemonDetailsViewModelFactory(detailUrl)
    )
    val pokemonDetailsResult = pokemonDetailsViewModel.getPokemonDetailsState

    UISkeleton(
        UISkeletonData(
            isLoading = pokemonDetailsResult.isLoading,
            isError = pokemonDetailsResult.isError,
            isDataEmpty = pokemonDetailsResult.pokemonDetailsData?.name?.isEmpty() == true
        ),
        {
            DisplayPokemonDetails(pokemonDetailsResult.pokemonDetailsData)
        }
    )
}

