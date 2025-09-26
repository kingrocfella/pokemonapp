package com.example.mypokemonapplication.common.utils

open class Utils {
    open fun getPokemonIdFromUrl(url: String): Int? {
        val pokemonId = url.split("/").dropLast(1).lastOrNull()?.toIntOrNull()
        return pokemonId
    }

    open fun capitalizeFirstLetter(word: String?): String {
        val newWord = word?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        } ?: "-"
        return newWord
    }
}


