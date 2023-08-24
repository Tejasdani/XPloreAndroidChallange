package com.xplor.android.challenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xplor.android.challenge.repository.models.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class XplorDatabase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDao

    companion object {
        const val DB_NAME = "xplor_db"
    }
}
