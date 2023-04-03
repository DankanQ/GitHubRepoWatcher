package com.example.githubrepowatcher.domain.usecases

import com.example.githubrepowatcher.domain.repository.AppRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(token: String) = appRepository.signIn(token)
}