package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository
import javax.inject.Inject

class GetRepositoryReadmeUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ) = appRepository.getRepositoryReadme(ownerName, repositoryName, branchName)
}