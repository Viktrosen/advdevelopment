package com.example.advdevelopment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.advdevelopment.view.main.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
