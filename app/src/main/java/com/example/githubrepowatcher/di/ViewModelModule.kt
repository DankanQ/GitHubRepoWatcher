package com.example.githubrepowatcher.di

import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.presentation.MainViewModel
import com.example.githubrepowatcher.presentation.auth.AuthViewModel
import com.example.githubrepowatcher.presentation.repo.RepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    @Binds
    fun bindRepoViewModel(impl: RepoViewModel): ViewModel
}