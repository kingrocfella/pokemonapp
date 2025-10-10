package com.example.mypokemonapplication.data.repository

import com.example.mypokemonapplication.data.api.ApiService
import com.example.mypokemonapplication.data.mapper.PokemonMapper.toDomain
import com.example.mypokemonapplication.domain.entities.PokemonDetail
import com.example.mypokemonapplication.domain.entities.PokemonListResult
import com.example.mypokemonapplication.domain.repository.PokemonRepository
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PokemonRepository {
    
    override suspend fun getPokemonList(limit: Int, offset: Int): Result<PokemonListResult> {
        return try {
            val response = apiService.getPokemonData(limit, offset)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getPokemonDetails(id: Int): Result<PokemonDetail> {
        return try {
            val response = apiService.getPokemonDetailsData(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

