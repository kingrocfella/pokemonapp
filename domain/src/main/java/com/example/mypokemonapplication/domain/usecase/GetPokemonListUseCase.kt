package com.example.mypokemonapplication.domain.usecase

import com.example.mypokemonapplication.domain.entities.PokemonListResult
import com.example.mypokemonapplication.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(limit: Int = 20, offset: Int = 0): Result<PokemonListResult> {
        return repository.getPokemonList(limit, offset)
    }
}

