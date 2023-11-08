package com.dogbreedexplorer.ui.breeds

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.repository.remote.BreedRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repo: BreedRepository): ViewModel() {

    var data: MutableLiveData<List<Breed>> = MutableLiveData()

    fun getDataObserver(): MutableLiveData<List<Breed>>{
        return data
    }

    fun getAllBreeds() {
        viewModelScope.launch {
            val response = repo.getAllBreeds()
            data.postValue(response.body())
        }
    }

}