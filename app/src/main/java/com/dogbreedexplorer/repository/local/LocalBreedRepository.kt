package com.dogbreedexplorer.repository.local

import com.dogbreedexplorer.db.dao.BreedDao
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class LocalBreedRepository (private val breedDao: BreedDao) {
    suspend fun insertAll(breeds: List<Breed>) {
        return breedDao.insertAllBreeds(breeds)
    }

    suspend fun allBreeds(): Response<List<Breed>> {
        val breeds = breedDao.getAll()
        return Response.success(breeds)
    }

    suspend fun getDetails(id: Int): Response<Breed> {
        val breed = breedDao.getDetails(id)
        return Response.success(breed)
    }
}