package com.example.githubrepowatcher.di

import com.example.githubrepowatcher.data.repository.AppRepositoryImpl
import com.example.githubrepowatcher.domain.repository.AppRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository
}