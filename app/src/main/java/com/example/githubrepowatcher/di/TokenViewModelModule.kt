package com.example.githubrepowatcher.di

import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.presentation.repo.RepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TokenViewModelModule {
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    @Binds
    fun bindRepoViewModel(impl: RepoViewModel): ViewModel
}