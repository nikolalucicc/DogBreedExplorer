package com.dogbreedexplorer.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.dogbreedexplorer.db.dao.BreedDao
import com.dogbreedexplorer.ui.breeds.MainViewModel
import com.dogbreedexplorer.ui.model.Breed

@Database(entities = [Breed::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao() : BreedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }
}