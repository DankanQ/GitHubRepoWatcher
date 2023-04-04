package com.example.githubrepowatcher.presentation.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.usecases.GetRepositoriesUseCase
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val authToken: String
) : ViewModel() {
    fun getAuthToken() = authToken

    private val _repositories = MutableLiveData<List<Repo>>()
    val repositories: LiveData<List<Repo>> = _repositories

    fun loadRepositories() {
        val repositories = arrayListOf<Repo>()
        repeat(10) {
            repositories.add(
                Repo(
                    "moko-web3",
                    "Kotlin",
                    "Ethereum Web3 implementation for mobile " +
                            "(android & ios) Kotlin Multiplatform development"
                )
            )
        }
        _repositories.value = repositories
    }
}