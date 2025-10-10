package com.example.mypokemonapplication.feature.pokemonlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypokemonapplication.models.UISkeletonData
import com.example.mypokemonapplication.feature.UISkeleton

@Composable
fun PokemonListScreen() {

    val pokemonListViewModel: PokemonListViewModel = hiltViewModel()
    val pokemonListDataResult by pokemonListViewModel.getPokemonListState.collectAsState()
    val state = pokemonListDataResult

    UISkeleton(
        UISkeletonData(
            isLoading = state.isLoading,
            isError = state.isError,
            isDataEmpty = state.data?.pokemons?.isEmpty() == true
        ),
        {
            DisplayPokemonList(
                state.data!!.pokemons,
                { pokemonListViewModel.getPaginatedPokemonList(true) },
                { url ->
                    pokemonListViewModel.setSelectedPokemonUrl(url)
                    pokemonListViewModel.navigateToDetails()
                },
                { pokemonListViewModel.getPaginatedPokemonList(false) }
            )
        }
    )
}
