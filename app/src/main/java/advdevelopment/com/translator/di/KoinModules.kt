package advdevelopment.com.translator.di

import advdevelopment.com.model.data.dto.SearchResultDto
import androidx.room.Room
//import advdevelopment.com.history.view.history.HistoryInteractor
//import advdevelopment.com.history.view.history.HistoryViewModel
import advdevelopment.com.repository.*
import advdevelopment.com.repository.room.HistoryDataBase
import advdevelopment.com.translator.view.main.MainActivity
import advdevelopment.com.translator.view.main.MainInteractor
import advdevelopment.com.translator.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResultDto>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResultDto>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}
