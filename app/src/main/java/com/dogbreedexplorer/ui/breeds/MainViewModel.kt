package com.dogbreedexplorer.ui.breeds

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.repository.local.LocalBreedRepository
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class BreedState {
    data class Loading(val isLoading: Boolean) : BreedState()
    data class Success(val breeds: List<Breed>) : BreedState()
    data class Error(val message: String) : BreedState()
}
class MainViewModel(
    private val repo: BreedRepository,
    private val localRepo: LocalBreedRepository
) : ViewModel() {

    private val _data: MutableStateFlow<BreedState> = MutableStateFlow(BreedState.Loading(false))
    val data: StateFlow<BreedState> = _data

    fun getAllBreeds(context: Context) {
        if (isNetworkConnected(context)) {
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

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            activeNetwork != null && connectivityManager.getNetworkCapabilities(activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            // Deprecated in API level 29 (Android 10)
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }
}
