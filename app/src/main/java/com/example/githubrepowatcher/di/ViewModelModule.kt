package com.example.githubrepowatcher.di

import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.presentation.MainViewModel
import com.example.githubrepowatcher.presentation.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {
    @IntoMap
    @StringKey("MainViewModel")
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @StringKey("AuthViewModel")
    @Binds
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel
}