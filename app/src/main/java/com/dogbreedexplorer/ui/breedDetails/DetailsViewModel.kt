package com.dogbreedexplorer.ui.breedDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.network.RetrofitInstance
import com.dogbreedexplorer.ui.model.Breed
import retrofit2.Call
import retrofit2.Response

class DetailsViewModel : ViewModel() {

    var data: MutableLiveData<Breed> = MutableLiveData()

    fun getDataObserver(): LiveData<Breed> {
        return data
    }

    fun getDetails(id: Int){
        val instance = RetrofitInstance.retrofit()
        val service = instance.create(Api::class.java)
        val call = service.getDetailsForBreed(id)
        call.enqueue(object : retrofit2.Callback<Breed> {
            override fun onResponse(call: Call<Breed>, response: Response<Breed>) {
                if(response.isSuccessful){
                    data.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Breed>, t: Throwable) {
                t.message?.let { Log.d("nikola", it) };
            }

        })
    }
}