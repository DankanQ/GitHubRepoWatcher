package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository

class SignInUseCase(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(token: String) = appRepository.signIn(token)
}