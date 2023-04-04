package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(repoId: String) = appRepository.getRepository(repoId)
}