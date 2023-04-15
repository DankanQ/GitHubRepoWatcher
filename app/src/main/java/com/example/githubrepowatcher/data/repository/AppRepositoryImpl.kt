package com.example.githubrepowatcher.data.repository

import com.example.githubrepowatcher.data.mapper.Mapper
import com.example.githubrepowatcher.data.network.ApiService
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.RepoDetails
import com.example.githubrepowatcher.domain.models.UserInfo
import com.example.githubrepowatcher.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper,
    private val keyValueStorage: KeyValueStorage
) : AppRepository {
    override suspend fun getRepositories(): List<Repo> {
        val repos = apiService.getRepositories(
            keyValueStorage.authToken!!.withBearer()
        )
        return repos.map {
            mapper.mapRepoDtoToModel(it)
        }
    }

    override suspend fun getRepository(repoId: String): RepoDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(token: String): UserInfo {
        val res = apiService.signIn(token.withBearer())
        if (res.isSuccessful) {
            keyValueStorage.authToken = token
        }
        return mapper.mapUserInfoDtoToModel(
            res.body()!!
        )
    }

    private fun String.withBearer() = "Bearer $this"
}