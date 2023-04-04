package com.example.githubrepowatcher.di

import com.example.githubrepowatcher.presentation.MainActivity
import com.example.githubrepowatcher.presentation.SplashFragment
import com.example.githubrepowatcher.presentation.auth.AuthFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: SplashFragment)

    fun inject(fragment: AuthFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
}