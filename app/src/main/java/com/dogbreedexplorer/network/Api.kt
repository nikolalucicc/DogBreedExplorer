package com.dogbreedexplorer.network

import retrofit2.Call
import com.dogbreedexplorer.ui.model.Breed
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("v1/breeds")
    fun getAllBreeds(): Call<List<Breed>>
    @GET("v1/breeds/{id}")
    fun getDetailsForBreed(@Path("id") id: Int): Call<Breed>
}