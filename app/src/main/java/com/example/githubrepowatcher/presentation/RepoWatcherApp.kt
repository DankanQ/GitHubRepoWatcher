package com.example.githubrepowatcher.presentation

import android.app.Application
import com.example.githubrepowatcher.di.DaggerAppComponent

class RepoWatcherApp: Application() {
    val component by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }
}