package com.example.githubrepowatcher.di

import android.app.Application
import com.example.githubrepowatcher.presentation.MainActivity
import com.example.githubrepowatcher.presentation.auth.AuthFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        SessionModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: AuthFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}