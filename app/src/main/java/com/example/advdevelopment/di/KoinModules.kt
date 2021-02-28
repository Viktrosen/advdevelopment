package com.example.advdevelopment.di

import androidx.room.Room
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.RetrofitImplementation
import com.example.advdevelopment.model.datasource.RoomDataBaseImplementation
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.model.repository.RepositoryImplementation
import com.example.advdevelopment.model.repository.RepositoryImplementationLocal
import com.example.advdevelopment.model.repository.RepositoryLocal
import com.example.advdevelopment.room.HistoryDataBase
import com.example.advdevelopment.view.history.HistoryInteractor
import com.example.advdevelopment.view.history.HistoryViewModel
import com.example.advdevelopment.view.main.MainInteractor
import com.example.advdevelopment.view.main.MainViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}
