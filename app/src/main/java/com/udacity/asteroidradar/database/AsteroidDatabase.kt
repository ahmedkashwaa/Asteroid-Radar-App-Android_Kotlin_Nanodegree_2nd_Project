package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Constants

@Database(version = 1,entities = [AsteroidTable::class])
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val dao: AsteroidDatabaseDao

    companion object {
        @Volatile
        private lateinit var instance: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if(!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid.db")
                        .build()
                }
            }
            return instance
        }
    }
}
