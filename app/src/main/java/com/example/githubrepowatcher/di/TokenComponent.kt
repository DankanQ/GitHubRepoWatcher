package com.example.githubrepowatcher.di

import com.example.githubrepowatcher.presentation.repo.RepoFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [TokenViewModelModule::class])
interface TokenComponent {
    fun inject(fragment: RepoFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance token: String): TokenComponent
    }
}