package com.example.githubrepowatcher.presentation.repo

import androidx.lifecycle.ViewModel
import com.example.githubrepowatcher.domain.usecases.GetRepositoriesUseCase
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val authToken: String
) : ViewModel() {
    fun getAuthToken() = authToken
}