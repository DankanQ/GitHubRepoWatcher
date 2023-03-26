package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository

class GetRepositoryReadmeUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ) = appRepository.getRepositoryReadme(ownerName, repositoryName, branchName)
}