package com.kelompok.uas_oop2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Tanaman::class, Customer::class), version = 1, exportSchema = false)
abstract class TanamanDB : RoomDatabase() {

    abstract fun tanamanDao(): TanamanDao
    abstract fun customerDao(): CustomerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TanamanDB? = null

        fun getDatabase(context: Context): TanamanDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    TanamanDB::class.java,
                    "adopsi.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}