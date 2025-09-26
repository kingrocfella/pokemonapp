package com.example.mypokemonapplication.pokemondetails

data class PokemonSpriteDetails(
    val front_default: String,
)

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: PokemonSpriteDetails
)
