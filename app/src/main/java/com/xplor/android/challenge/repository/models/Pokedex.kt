package com.xplor.android.challenge.repository.models

data class Pokedex(
    val id: Long,
    val name: String,
    val pokemonEntries: List<Pokemon>
)
