package com.xplor.android.challenge.di.modules

import android.content.Context
import androidx.room.Room
import com.xplor.android.challenge.database.XplorDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDb(applicationContext: Context): XplorDatabase {
        return Room.databaseBuilder(
            applicationContext,
            XplorDatabase::class.java,
            XplorDatabase.DB_NAME
        ).build()
    }

}
