package com.dogbreedexplorer.network

import retrofit2.Call
import com.dogbreedexplorer.ui.model.Breed
import retrofit2.http.GET

interface Api {

    @GET("v1/breeds")
    fun getAllBreeds(): Call<List<Breed>>
}