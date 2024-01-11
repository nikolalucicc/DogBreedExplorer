package com.dogbreedexplorer.network

import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.model.Favorite
import com.dogbreedexplorer.utils.model.dao.FavoriteDao
import com.dogbreedexplorer.utils.model.Vote
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v1/breeds")
    suspend fun getAllBreeds(): Response<List<Breed>>
    @GET("v1/breeds/{id}")
    suspend fun getDetailsForBreed(@Path("id") id: Int): Response<Breed>
    @GET("v1/breeds/search")
    suspend fun searchBreed(@Query("q") q: String): Response<List<Breed>>

    @Headers(
        "Content-Type: application/json",
        "x-api-key: live_l9zKc8SRMYb1JFqTlZl4pmXo6aJTN5ovmcD6Dc46xGhWwl2H0XZ8hko0UT1fc4jm"
    )
    @POST("v1/votes")
    suspend fun sendVote(@Body vote: Vote): Response<Any>

    @Headers(
        "Content-Type: application/json",
        "x-api-key: live_l9zKc8SRMYb1JFqTlZl4pmXo6aJTN5ovmcD6Dc46xGhWwl2H0XZ8hko0UT1fc4jm"
    )
    @POST("v1/favourites")
    suspend fun addToFavourite(@Body favoriteDao: FavoriteDao): Response<Any>

    @Headers(
        "Content-Type: application/json",
        "x-api-key: live_l9zKc8SRMYb1JFqTlZl4pmXo6aJTN5ovmcD6Dc46xGhWwl2H0XZ8hko0UT1fc4jm"
    )
    @GET("v1/favourites")
    suspend fun getAllFavourites(): Response<List<Favorite>>

    @Headers(
        "Content-Type: application/json",
        "x-api-key: live_l9zKc8SRMYb1JFqTlZl4pmXo6aJTN5ovmcD6Dc46xGhWwl2H0XZ8hko0UT1fc4jm"
    )
    @DELETE("v1/favourites/{favourite_id}")
    suspend fun deleteFavorite(@Path("favourite_id") favourite_id: String): Response<ResponseBody>

}