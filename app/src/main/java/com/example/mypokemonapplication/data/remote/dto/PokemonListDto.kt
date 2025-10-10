package com.example.mypokemonapplication.data.remote.dto


data class PokemonListItemDto(
    val name: String,
    val url: String
)

/**
 * Data Transfer Object for paginated Pokemon list which represents the raw API response structure
 */
data class PokemonListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItemDto>
)

