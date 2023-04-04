package com.example.githubrepowatcher.data.repository

import com.example.githubrepowatcher.data.mapper.Mapper
import com.example.githubrepowatcher.data.network.ApiService
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.RepoDetails
import com.example.githubrepowatcher.domain.models.UserInfo
import com.example.githubrepowatcher.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper
) : AppRepository {
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
        return mapper.mapUserInfoDtoToModel(
            apiService.signIn(token)
        )
    }
}