package com.example.mypokemonapplication.data.remote.dto

data class PokemonSpritesDto(
    val front_default: String
)

/**
 * Data Transfer Object for Pokemon details which represents the raw API response 
 */
data class PokemonDetailsDto(
    val id: Int,
    val name: String,
    val height: Int,
    val sprites: PokemonSpritesDto
)

