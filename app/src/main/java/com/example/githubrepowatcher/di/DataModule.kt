package com.example.githubrepowatcher.di

import com.example.githubrepowatcher.data.network.ApiFactory
import com.example.githubrepowatcher.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    @AppScope
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}