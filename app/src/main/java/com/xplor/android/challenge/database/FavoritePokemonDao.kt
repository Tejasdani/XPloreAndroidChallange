package com.xplor.android.challenge.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xplor.android.challenge.repository.models.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {

    //todo need to remove suspends keyword as Flow is asynchronous process and we cant give
    // flows with suspend function
    @Query("Select * FROM ${Pokemon.TABLE_NAME}")
    fun getAllFavoritePokemon(): Flow<List<Pokemon>>

    @Query("Select * FROM ${Pokemon.TABLE_NAME}")
    suspend fun getAllFavoritePokemonAsync(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)
}
