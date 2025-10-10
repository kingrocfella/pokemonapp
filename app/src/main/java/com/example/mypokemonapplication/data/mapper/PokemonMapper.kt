package com.example.mypokemonapplication.data.mapper

import com.example.mypokemonapplication.data.remote.dto.PokemonDetailsDto
import com.example.mypokemonapplication.data.remote.dto.PokemonListDto
import com.example.mypokemonapplication.data.remote.dto.PokemonListItemDto
import com.example.mypokemonapplication.domain.entities.Pokemon
import com.example.mypokemonapplication.domain.entities.PokemonDetail
import com.example.mypokemonapplication.domain.entities.PokemonListResult

/**
  Mapper class to convert between Data layer DTOs and Domain layer entities
 */
object PokemonMapper {
    
    fun PokemonListItemDto.toDomain(): Pokemon {
        return Pokemon(
            name = name,
            url = url
        )
    }

    fun PokemonListDto.toDomain(): PokemonListResult {
        return PokemonListResult(
            count = count,
            next = next,
            previous = previous,
            pokemons = results.map { it.toDomain() }
        )
    }
    
    fun PokemonDetailsDto.toDomain(): PokemonDetail {
        return PokemonDetail(
            id = id,
            name = name,
            height = height,
            imageUrl = sprites.front_default
        )
    }
}

