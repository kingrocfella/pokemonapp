package com.example.mypokemonapplication.domain.repository

import com.example.mypokemonapplication.domain.entities.PokemonDetail
import com.example.mypokemonapplication.domain.entities.PokemonListResult


interface PokemonRepository {

    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): Result<PokemonListResult>
    
    suspend fun getPokemonDetails(id: Int): Result<PokemonDetail>
}

