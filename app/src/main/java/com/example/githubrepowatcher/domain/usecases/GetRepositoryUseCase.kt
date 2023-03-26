package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository

class GetRepositoryUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(repoId: String) = appRepository.getRepository(repoId)
}