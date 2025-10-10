package com.example.mypokemonapplication.domain.entities

data class PokemonListResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemons: List<Pokemon>
)

