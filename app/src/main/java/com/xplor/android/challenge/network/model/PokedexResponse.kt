package com.xplor.android.challenge.network.model

import com.google.gson.annotations.SerializedName
import com.xplor.android.challenge.repository.models.Pokedex
import com.xplor.android.challenge.repository.models.Pokemon

data class PokedexResponse(
    val id: Long,
    val name: String,
    @SerializedName("pokemon_entries") val pokemonEntries: List<PokemonEntryResponse>
)

data class PokemonEntryResponse(
    @SerializedName("entry_number") val entryNumber: Long,
    @SerializedName("pokemon_species") val pokemonSpecies: PokemonResponse
)

data class PokemonResponse(
    val name: String,
    val url: String
)

fun PokedexResponse.toPokedex(): Pokedex {
    return Pokedex(
        id = this.id,
        name = this.name,
        pokemonEntries = this.pokemonEntries.map { it.toPokemon() }
    )
}

fun PokemonEntryResponse.toPokemon(): Pokemon {
    return Pokemon(
        entryNumber = this.entryNumber,
        name = this.pokemonSpecies.name,
        url = this.pokemonSpecies.url
    )
}
