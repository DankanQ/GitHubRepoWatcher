package com.example.githubrepowatcher.domain.repository

import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.RepoDetails
import com.example.githubrepowatcher.domain.models.UserInfo

interface AppRepository {
    suspend fun getRepositories(): List<Repo>

    suspend fun getRepository(repoId: String): RepoDetails

    suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String

    suspend fun signIn(token: String): UserInfo
}