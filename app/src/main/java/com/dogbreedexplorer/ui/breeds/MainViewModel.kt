package com.dogbreedexplorer.ui.breeds

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.repository.remote.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

sealed class BreedState {
    data class Loading(val isLoading: Boolean) : BreedState()
    data class Success(val breeds: List<Breed>) : BreedState()
    data class Error(val message: String) : BreedState()
}
class MainViewModel(private val repo: BreedRepository) : ViewModel() {

    private val _data: MutableStateFlow<BreedState> = MutableStateFlow(BreedState.Loading(false))
    val data: StateFlow<BreedState> = _data

    fun getAllBreeds() {
        viewModelScope.launch {
            try {
                _data.value = BreedState.Loading(true)
                val response = repo.getAllBreeds()
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