package com.dogbreedexplorer.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogbreedexplorer.ui.model.Breed
import com.dogbreedexplorer.utils.network.Api
import com.dogbreedexplorer.utils.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    var data: MutableLiveData<List<Breed>> = MutableLiveData()

    fun getDataObserver(): MutableLiveData<List<Breed>>{
        return data
    }

    fun getAllBreeds(){
        val instance = RetrofitInstance.retrofit()
        val service = instance.create(Api::class.java)
        val call = service.getAllBreeds()
        call.enqueue(object : Callback<List<Breed>> {
            override fun onResponse(call: Call<List<Breed>>, response: Response<List<Breed>>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Breed>>, t: Throwable) {
                t.message?.let { Log.d("nikola", it) };
            }

        })
    }

}