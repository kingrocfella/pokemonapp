package com.example.mypokemonapplication.data.models

data class PokemonSpriteDetails(
    val front_default: String,
)

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: PokemonSpriteDetails
)
