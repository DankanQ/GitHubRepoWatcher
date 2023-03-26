package com.example.githubrepowatcher.domain.repository

import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.RepoDetails
import com.example.githubrepowatcher.domain.models.UserInfo

interface AppRepository {
    suspend fun getRepositories(): List<Repo> {
        TODO()
    }

    suspend fun getRepository(repoId: String): RepoDetails {
        TODO()
    }

    suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        TODO()
    }

    suspend fun signIn(token: String): UserInfo {
        TODO()
    }
}