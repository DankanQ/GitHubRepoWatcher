package com.example.githubrepowatcher.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.presentation.auth.AuthFragmentDirections
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SessionCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val mainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (application as RepoWatcherApp).component
            .mainComponentFactory()
            .create()
    }

    private val navController by lazy {
        val navHostFragment = (supportFragmentManager.findFragmentById(
            R.id.fragment_container_view
        ) as NavHostFragment)
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun startSession(authToken: String) {
        mainViewModel.saveAuthToken(KeyValueStorage(authToken))
        navController.navigate(
            AuthFragmentDirections.actionAuthFragmentToRepoFragment(authToken)
        )
    }

    override fun endSession() {
        mainViewModel.clearAuthToken()
        with(navController) {
            popBackStack()
            navigate(R.id.authFragment)
        }
    }
}