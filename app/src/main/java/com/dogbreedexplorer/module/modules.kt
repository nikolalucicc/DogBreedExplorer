package com.dogbreedexplorer.module

import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.network.RetrofitInstance
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.breeds.BreedsFragment
import com.dogbreedexplorer.ui.breeds.MainViewModel
import org.koin.dsl.module

val viewModule = module {
    factory { MainViewModel(get()) }
    single { BreedRepository(get()) }
    factory { BreedsFragment() }
    single { RetrofitInstance.retrofit().create(Api::class.java) }
}