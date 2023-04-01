package com.example.githubrepowatcher.data.repository

import com.example.githubrepowatcher.data.mapper.GitHubMapper
import com.example.githubrepowatcher.data.network.ApiFactory
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.RepoDetails
import com.example.githubrepowatcher.domain.models.UserInfo
import com.example.githubrepowatcher.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {
    private val apiService = ApiFactory.apiService

    private val gitHubMapper = GitHubMapper()

    override suspend fun getRepositories(): List<Repo> {
        TODO("Not yet implemented")
    }

    override suspend fun getRepository(repoId: String): RepoDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String,
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(token: String): UserInfo {
        return gitHubMapper.mapUserInfoDtoToModel(
            apiService.signIn(token)
        )
    }
}