package com.example.mypokemonapplication.pokemonlist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mypokemonapplication.common.ui.UISkeleton
import com.example.mypokemonapplication.common.ui.UISkeletonData

@Composable
fun PokemonListScreen(handleNavigateToDetailsScreen: (url: String) -> Unit) {

    val pokemonListViewModel = viewModel<PokemonListViewModel>()
    val pokemonListDataResult = pokemonListViewModel.getPokemonListState

    UISkeleton(
        UISkeletonData(
            isLoading = pokemonListDataResult.isLoading,
            isError = pokemonListDataResult.isError,
            isDataEmpty = pokemonListDataResult.pokemonList?.results?.isEmpty() == true
        ),
        {
            DisplayPokemonList(
                pokemonListDataResult.pokemonList!!.results,
                { pokemonListViewModel.getPaginatedPokemonList(true) },
                handleNavigateToDetailsScreen,
                { pokemonListViewModel.getPaginatedPokemonList(false) }
            )
        }
    )
}
