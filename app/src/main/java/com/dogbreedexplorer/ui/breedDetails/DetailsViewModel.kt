package com.dogbreedexplorer.ui.breedDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailsViewModel(private val repo: BreedRepository) : ViewModel() {

    var data: MutableLiveData<Breed> = MutableLiveData()

    fun getDataObserver(): LiveData<Breed> {
        return data
    }

    fun getDetails(id: Int){
        viewModelScope.launch {
            val response = repo.getDetailsForBreed(id)
            data.postValue(response.body())
        }
    }
}