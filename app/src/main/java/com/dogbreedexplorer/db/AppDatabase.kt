package com.dogbreedexplorer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dogbreedexplorer.db.dao.BreedDao
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.converter.ImageConverter
import com.dogbreedexplorer.utils.model.Favorite
import com.dogbreedexplorer.utils.model.Image
import org.koin.dsl.module

@Database(entities = [Breed::class, Favorite::class], version = 4, exportSchema = false)
@TypeConverters(ImageConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao() : BreedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }
}