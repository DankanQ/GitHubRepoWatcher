package com.example.githubrepowatcher.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        SessionModule::class
    ]
)
interface AppComponent {
    fun mainComponentFactory(): MainComponent.Factory

    fun tokenComponentFactory(): TokenComponent.Factory

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}