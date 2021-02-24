package com.example.advdevelopment.di

import com.example.advdevelopment.di.NAME_LOCAL
import com.example.advdevelopment.di.NAME_REMOTE
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.RetrofitImplementation
import com.example.advdevelopment.model.datasource.RoomDataBaseImplementation
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.model.repository.RepositoryImplementation
import com.example.advdevelopment.view.main.MainInteractor
import com.example.advdevelopment.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(RetrofitImplementation()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImplementation(RoomDataBaseImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}
