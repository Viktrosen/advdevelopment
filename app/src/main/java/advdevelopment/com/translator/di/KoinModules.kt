package advdevelopment.com.translator.di

import androidx.room.Room
//import advdevelopment.com.history.view.history.HistoryInteractor
//import advdevelopment.com.history.view.history.HistoryViewModel
import advdevelopment.com.model.data.DataModel
import advdevelopment.com.model.room.HistoryDataBase
import advdevelopment.com.repository.*
import advdevelopment.com.translator.view.main.MainInteractor
import advdevelopment.com.translator.view.main.MainViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}
