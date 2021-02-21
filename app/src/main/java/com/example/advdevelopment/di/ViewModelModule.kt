package com.example.advdevelopment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.advdevelopment.di.InteractorModule
import com.example.advdevelopment.di.ViewModelFactory
import com.example.advdevelopment.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.example.advdevelopment.view.main.MainViewModel

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}
