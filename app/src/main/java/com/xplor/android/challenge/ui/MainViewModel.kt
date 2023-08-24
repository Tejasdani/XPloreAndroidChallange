package com.xplor.android.challenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xplor.android.challenge.repository.MainRepository
import com.xplor.android.challenge.repository.models.Pokedex
import com.xplor.android.challenge.repository.models.Pokemon
import com.xplor.android.challenge.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _pokedex = MutableStateFlow<ApiState<List<Pokemon>>>(ApiState.Loading)
    val pokedex: StateFlow<ApiState<List<Pokemon>>> = _pokedex

    private val _favoritePokemon = MutableStateFlow<ApiState<List<Pokemon>>>(ApiState.Loading)
    val favoritePokemon: StateFlow<ApiState<List<Pokemon>>> = _favoritePokemon

    init {
        fetchListFromApi()
        fetchFavoritePokemonFromDb()
    }

    fun setPokemonAsFavorite(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setPokemonAsFavorite(pokemon)
        }
    }

    fun removePokemonFromFavorite(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removePokemonAsFavorite(pokemon)
        }
    }

    private fun fetchListFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchApiList().collect {
                _pokedex.value = it
            }
        }
    }

    private fun fetchFavoritePokemonFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoritePokemon().collect {
                _favoritePokemon.value = it
            }
        }
    }
}
