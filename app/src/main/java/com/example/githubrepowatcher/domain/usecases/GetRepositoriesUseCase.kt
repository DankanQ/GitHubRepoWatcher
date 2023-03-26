package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository

class GetRepositoriesUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke() = appRepository.getRepositories()
}