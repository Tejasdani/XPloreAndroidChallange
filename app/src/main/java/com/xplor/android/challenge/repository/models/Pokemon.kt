package com.xplor.android.challenge.repository.models

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Pokemon.TABLE_NAME)
data class Pokemon(
    @PrimaryKey
    val entryNumber: Long,
    val name: String,
    val url: String,
    var isFavorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "pokemon_table"
    }
}
