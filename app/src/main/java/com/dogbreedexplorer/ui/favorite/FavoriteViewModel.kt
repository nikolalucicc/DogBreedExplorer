package com.dogbreedexplorer.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.repository.local.LocalBreedRepository
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.NetworkUtil
import com.dogbreedexplorer.utils.model.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

sealed class FavoriteState {
    data class Loading(val isLoading: Boolean) : FavoriteState()
    data class Success(val favBreed: List<Favorite>, val breedList: List<Breed>) : FavoriteState()
    data class Error(val message: String) : FavoriteState()
}

class FavouriteViewModel(
    private val repo: BreedRepository,
    private val networkUtil: NetworkUtil,
    private val localRepo: LocalBreedRepository
) : ViewModel() {

    private val _data: MutableStateFlow<FavoriteState> = MutableStateFlow(FavoriteState.Loading(false))
    val data: StateFlow<FavoriteState> = _data

    fun getFavorites(context: Context) {
        if (networkUtil.isNetworkConnected(context)) {
            viewModelScope.launch {
                try {
                    _data.value = FavoriteState.Loading(true)
                    val response = repo.getAllFavourites()
                    val favBreed = response.body() ?: emptyList()

                    val breedListResponse = repo.getAllBreeds()
                    val breedList = breedListResponse.body() ?: emptyList()

                    if(response.isSuccessful){
                        _data.value = FavoriteState.Success(favBreed, breedList)
                        val fav = response.body()
                        if(fav != null){
                            localRepo.insertAllFavorite(fav)
                        }
                    } else {
                        _data.value = FavoriteState.Error("Error fetching favorites: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = FavoriteState.Error("Exception while fetching favorites: ${e.message}")
                } finally {
                    _data.value = FavoriteState.Loading(false)
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    _data.value = FavoriteState.Loading(true)
                    val response = localRepo.allFavorites()
                    val favBreed = response.body() ?: emptyList()

                    val breedListResponse = localRepo.allBreeds()
                    val breedList = breedListResponse.body() ?: emptyList()

                    if (response.isSuccessful) {
                        _data.value = FavoriteState.Success(favBreed, breedList)
                    } else {
                        _data.value = FavoriteState.Error("Error fetching favorites: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = FavoriteState.Error("Exception while fetching favorites: ${e.message}")
                } finally {
                    _data.value = FavoriteState.Loading(false)
                }
            }
        }
    }
}
