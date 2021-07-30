package com.sachin.milkdistributor.room

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.sample.models.Details
import com.example.sample.room.DetailsDao

@Database(entities = [Details::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun detailsDao(): DetailsDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}