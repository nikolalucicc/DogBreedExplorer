package com.dogbreedexplorer.ui.breedDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

sealed class BreedDetailsState {
    data class Loading(val isLoading: Boolean) : BreedDetailsState()
    data class Success(val breedDetails: Breed?) : BreedDetailsState()
    data class Error(val message: String) : BreedDetailsState()
}
class DetailsViewModel(private val repo: BreedRepository) : ViewModel() {

    private val _data: MutableStateFlow<BreedDetailsState> = MutableStateFlow(BreedDetailsState.Loading(false))
    private val data: StateFlow<BreedDetailsState> = _data

    fun getDataObserver(): StateFlow<BreedDetailsState> {
        return data
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            try {
                _data.value = BreedDetailsState.Loading(true)
                val response = repo.getDetailsForBreed(id)
                if (response.isSuccessful) {
                    _data.value = BreedDetailsState.Success(response.body())
                } else {
                    _data.value = BreedDetailsState.Error("Error fetching details: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _data.value = BreedDetailsState.Error("Exception while fetching details: ${e.message}")
            } finally {
                _data.value = BreedDetailsState.Loading(false)
            }
        }
    }
}
