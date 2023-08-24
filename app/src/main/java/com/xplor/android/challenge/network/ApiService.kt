package com.xplor.android.challenge.network

import com.xplor.android.challenge.network.model.PokedexResponse
import retrofit2.http.GET

interface ApiService {

    @GET("pokedex/2/")
    suspend fun fetchNationalPokedex(): PokedexResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

}
