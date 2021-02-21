package com.example.advdevelopment.di

import com.example.advdevelopment.di.NAME_LOCAL
import com.example.advdevelopment.di.NAME_REMOTE
import dagger.Module
import dagger.Provides
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.view.main.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
