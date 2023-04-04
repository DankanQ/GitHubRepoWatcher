package com.example.githubrepowatcher.di

import com.example.githubrepowatcher.data.network.ApiFactory
import com.example.githubrepowatcher.data.network.ApiService
import com.example.githubrepowatcher.data.repository.AppRepositoryImpl
import com.example.githubrepowatcher.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    @AppScope
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository

    companion object {
        @Provides
        @AppScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}