package com.dogbreedexplorer.network

import android.database.Observable
import retrofit2.Call
import com.dogbreedexplorer.ui.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v1/breeds")
    suspend fun getAllBreeds(): Response<List<Breed>>
    @GET("v1/breeds/{id}")
    suspend fun getDetailsForBreed(@Path("id") id: Int): Response<Breed>
    @GET("v1/breeds/search")
    suspend fun searchBreed(@Query("q") q: String): Response<List<Breed>>
}