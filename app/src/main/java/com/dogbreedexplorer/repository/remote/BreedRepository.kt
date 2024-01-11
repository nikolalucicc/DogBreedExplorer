package com.dogbreedexplorer.repository.remote

import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.model.Favorite
import com.dogbreedexplorer.utils.model.dao.FavoriteDao
import com.dogbreedexplorer.utils.model.Vote
import okhttp3.ResponseBody
import retrofit2.Response

class BreedRepository(private val api: Api) {
    suspend fun getAllBreeds(): Response<List<Breed>> {
        return api.getAllBreeds()
    }
    suspend fun getDetailsForBreed(id: Int): Response<Breed>{
        return api.getDetailsForBreed(id)
    }
    suspend fun searchBreed(q: String): Response<List<Breed>> {
        return api.searchBreed(q)
    }
    suspend fun sendVote(vote: Vote): Response<Any>{
        return api.sendVote(vote)
    }
    suspend fun addToFavourite(favoriteDao: FavoriteDao): Response<Any>{
        return api.addToFavourite(favoriteDao)
    }
    suspend fun getAllFavourites(): Response<List<Favorite>>{
        return api.getAllFavourites()
    }
    suspend fun deleteFavorite(favourite_id: String): Response<ResponseBody>{
        return api.deleteFavorite(favourite_id)
    }
}