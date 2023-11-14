package com.dogbreedexplorer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBreeds(breeds: List<Breed>)

    @Query("SELECT * FROM breed")
    suspend fun getAll(): List<Breed>

    @Query("SELECT * FROM breed WHERE id = :id")
    suspend fun getDetails(id: Int): Breed
}