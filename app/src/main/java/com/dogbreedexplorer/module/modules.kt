package com.dogbreedexplorer.module

import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.network.RetrofitInstance
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.breedDetails.DetailsViewModel
import com.dogbreedexplorer.ui.breeds.MainViewModel
import org.koin.dsl.module

val viewModule = module {
    factory { DetailsViewModel(get()) }
    factory { MainViewModel(get()) }
}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(Api::class.java) }
}

val repositoryModule = module {
    single { BreedRepository(get()) }
}