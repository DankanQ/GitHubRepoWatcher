package com.example.githubrepowatcher.di

import android.app.Application
import android.os.Build
import com.example.githubrepowatcher.data.SessionManager
import com.example.githubrepowatcher.data.repository.SessionRepositoryImpl
import com.example.githubrepowatcher.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface SessionModule {
    @Binds
    fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository

    companion object {
        @Provides
        fun provideSessionManager(application: Application): SessionManager {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SessionManager.SessionManagerAPI23()
            } else {
                SessionManager.SessionManagerAPI21(application)
            }
        }
    }
}