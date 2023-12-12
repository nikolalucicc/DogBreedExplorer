package com.dogbreedexplorer.repository.remote

import android.database.Observable
import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.model.Vote
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path

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
    suspend fun getVotes(): Response<Any>{
        return api.getVotes()
    }
}