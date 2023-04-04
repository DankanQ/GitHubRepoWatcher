package com.example.githubrepowatcher.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.presentation.auth.AuthFragment
import com.example.githubrepowatcher.presentation.repo.RepoFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SessionCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        (application as RepoWatcherApp).component
            .mainComponentFactory()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAuth()
    }

    private fun checkAuth() {
        val keyValueStorage = mainViewModel.getAuthToken()
        val authToken = keyValueStorage?.authToken
        val startFragment = if (authToken != null) {
            RepoFragment.newInstance(authToken)
        } else {
            AuthFragment.newInstance()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, startFragment)
            .commit()
    }

    override fun startSession(authToken: String) {
        mainViewModel.saveAuthToken(KeyValueStorage(authToken))
        val repoFragment = RepoFragment.newInstance(authToken)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, repoFragment)
            .commit()
    }

    override fun endSession() {
        mainViewModel.clearAuthToken()
        val authFragment = AuthFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, authFragment)
            .commit()
    }
}