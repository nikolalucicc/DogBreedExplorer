package com.dogbreedexplorer.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.repository.local.LocalBreedRepository
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.NetworkUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

sealed class SearchState {
    data class Loading(val isLoading: Boolean) : SearchState()
    data class Success(val searchBreed: List<Breed>) : SearchState()
    data class Error(val message: String) : SearchState()
}
class SearchViewModel(
    private val repo: BreedRepository,
    private val localRepo: LocalBreedRepository,
    private val networkUtil: NetworkUtil
) : ViewModel() {

    private val _data: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Loading(false))
    val data: StateFlow<SearchState> = _data

    fun searchBreed(q: String, context: Context){
        if(networkUtil.isNetworkConnected(context)){
            viewModelScope.launch {
                try {
                    _data.value = SearchState.Loading(true)
                    val response = repo.searchBreed(q)
                    if (response.isSuccessful) {
                        _data.value = SearchState.Success(response.body() ?: emptyList())
                    } else {
                        _data.value = SearchState.Error("Error fetching breeds: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = SearchState.Error("Exception while fetching breeds: ${e.message}")
                } finally {
                    _data.value = SearchState.Loading(false)
                }
            }
        } else{
            viewModelScope.launch {
                try {
                    _data.value = SearchState.Loading(true)
                    val response = localRepo.searchBreed(q)
                    if (response.isSuccessful) {
                        _data.value = SearchState.Success(response.body() ?: emptyList())
                    } else {
                        _data.value = SearchState.Error("Error fetching breeds: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    _data.value = SearchState.Error("Exception while fetching breeds: ${e.message}")
                } finally {
                    _data.value = SearchState.Loading(false)
                }
            }
        }
    }
}