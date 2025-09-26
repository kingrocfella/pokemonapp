package com.example.mypokemonapplication.pokemonlist

data class PokemonListResult(val name: String, val url: String)

data class PokemonList(val count: Int, val next: String?, val previous: String?, val results: List<PokemonListResult>)
