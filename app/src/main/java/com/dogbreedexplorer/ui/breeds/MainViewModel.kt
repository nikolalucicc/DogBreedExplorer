package com.dogbreedexplorer.ui.breeds

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.app.App
import com.dogbreedexplorer.repository.local.LocalBreedRepository
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.NetworkUtil
import com.dogbreedexplorer.utils.model.Favourite
import com.dogbreedexplorer.utils.model.Vote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


sealed class BreedState {
    data class Loading(val isLoading: Boolean) : BreedState()
    data class Success(val breeds: List<Breed>) : BreedState()
    data class Error(val message: String) : BreedState()
}
class MainViewModel(
    private val repo: BreedRepository,
    private val localRepo: LocalBreedRepository,
    private val networkUtil: NetworkUtil
) : ViewModel() {

    private val _data: MutableStateFlow<BreedState> = MutableStateFlow(BreedState.Loading(false))
    val data: StateFlow<BreedState> = _data

    fun getAllBreeds(context: Context) {
        if (networkUtil.isNetworkConnected(context)) {
            viewModelScope.launch {
                try {
                    _data.value = BreedState.Loading(true)
                    val response = repo.getAllBreeds()
                    if (response.isSuccessful) {
                        _data.value = BreedState.Success(response.body() ?: emptyList())
                        val breeds = response.body()
                        if (breeds != null) {
                            localRepo.insertAll(breeds)
                        }
                    } else {
                        _data.value = BreedState.Error("Error fetching breeds: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = BreedState.Error("Exception while fetching breeds: ${e.message}")
                } finally {
                    _data.value = BreedState.Loading(false)
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    _data.value = BreedState.Loading(true)
                    val response = localRepo.allBreeds()
                    if (response.isSuccessful) {
                        _data.value = BreedState.Success(response.body() ?: emptyList())
                    } else {
                        _data.value = BreedState.Error("Error fetching breeds: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = BreedState.Error("Exception while fetching breeds: ${e.message}")
                } finally {
                    _data.value = BreedState.Loading(false)
                }
            }
        }
    }

    suspend fun addToFavourite(imageId: String, subId: String?) {
        val requestBody = Favourite(imageId, null)
        val response = repo.addToFavourite(requestBody)

        if (!response.isSuccessful) {
            _data.value = BreedState.Error("Error adding to favourite: ${response.code()} - ${response.message()}")
        }
    }

}
