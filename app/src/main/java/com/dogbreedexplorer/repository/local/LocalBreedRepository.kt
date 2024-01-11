package com.dogbreedexplorer.repository.local

import com.dogbreedexplorer.db.dao.BreedDao
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.model.Favorite
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

    suspend fun searchBreed(q: String): Response<List<Breed>>{
        val breed = breedDao.searchBreed(q)
        return Response.success(breed)
    }

    suspend fun insertAllFavorite(favorites: List<Favorite>) {
        return breedDao.insertAllFavorite(favorites)
    }

    suspend fun allFavorites(): Response<List<Favorite>> {
        val fav = breedDao.getAllFavorite()
        return Response.success(fav)
    }

    suspend fun clearTable() {
        breedDao.clearTable()
    }

    suspend fun deleteFavorite(favorite_id: String): Response<Unit> {
        val favorite = breedDao.deleteFavorite(favorite_id)
        return Response.success(favorite)
    }
}