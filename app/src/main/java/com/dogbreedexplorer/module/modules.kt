package com.dogbreedexplorer.module

import android.content.Context
import androidx.room.Room
import com.dogbreedexplorer.db.AppDatabase
import com.dogbreedexplorer.network.Api
import com.dogbreedexplorer.network.RetrofitInstance
import com.dogbreedexplorer.repository.local.LocalBreedRepository
import com.dogbreedexplorer.repository.remote.BreedRepository
import com.dogbreedexplorer.ui.breedDetails.DetailsViewModel
import com.dogbreedexplorer.ui.breeds.MainViewModel
import com.dogbreedexplorer.ui.search.SearchViewModel
import com.dogbreedexplorer.utils.NetworkUtil
import org.koin.dsl.module

val viewModule = module {
    factory { DetailsViewModel(get(), get(), get()) }
    factory { MainViewModel(get(), get(), get()) }
    factory { SearchViewModel(get()) }
}

val networkModule = module {
    single { RetrofitInstance.retrofit().create(Api::class.java) }
    single { NetworkUtil() }
}

val repositoryModule = module {
    single { BreedRepository(get()) }
    single { LocalBreedRepository(get()) }
}

fun dbModule(context: Context) = module {
    single {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "breed"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().breedDao() }
}