package com.example.mypokemonapplication.domain.usecase

import com.example.mypokemonapplication.domain.entities.PokemonDetail
import com.example.mypokemonapplication.domain.repository.PokemonRepository
import javax.inject.Inject


class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(id: Int): Result<PokemonDetail> {
        return repository.getPokemonDetails(id)
    }
}

