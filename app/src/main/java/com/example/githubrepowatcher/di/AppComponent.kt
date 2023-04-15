package com.example.githubrepowatcher.di

import android.app.Application
import com.example.githubrepowatcher.presentation.MainActivity
import com.example.githubrepowatcher.presentation.SplashFragment
import com.example.githubrepowatcher.presentation.auth.AuthFragment
import com.example.githubrepowatcher.presentation.repo.RepoFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        SessionModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: SplashFragment)

    fun inject(fragment: AuthFragment)

    fun inject(fragment: RepoFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}