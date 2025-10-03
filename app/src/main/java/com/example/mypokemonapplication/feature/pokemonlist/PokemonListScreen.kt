package com.example.mypokemonapplication.feature.pokemonlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypokemonapplication.data.models.UISkeletonData
import com.example.mypokemonapplication.data.models.ViewModelState
import com.example.mypokemonapplication.feature.common.UISkeleton

@Composable
fun PokemonListScreen() {

    val pokemonListViewModel: PokemonListViewModel = hiltViewModel()
    val pokemonListDataResult by pokemonListViewModel.getPokemonListState.collectAsState()
    val state = pokemonListDataResult

    UISkeleton(
        UISkeletonData(
            isLoading = state.isLoading,
            isError = state.isError,
            isDataEmpty = state.data?.results?.isEmpty() == true
        ),
        {
            if (state.data != null) {
                DisplayPokemonList(
                    state.data.results,
                    { pokemonListViewModel.getPaginatedPokemonList(true) },
                    { url ->
                        pokemonListViewModel.setSelectedPokemonUrl(url)
                        pokemonListViewModel.navigateToDetails()
                    },
                    { pokemonListViewModel.getPaginatedPokemonList(false) }
                )
            }
        }
    )
}
