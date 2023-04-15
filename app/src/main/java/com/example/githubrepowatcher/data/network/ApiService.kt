package com.example.githubrepowatcher.data.network

import com.example.githubrepowatcher.data.network.models.RepoDetailsDto
import com.example.githubrepowatcher.data.network.models.RepoDto
import com.example.githubrepowatcher.data.network.models.UserInfoDto
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user/repos")
    suspend fun getRepositories(
        @Header(HEADER_PARAM_TOKEN) token: String
    ): List<RepoDto>

    @GET("repositories/{repoId}")
    suspend fun getRepository(
        @Path(PATH_PARAM_REPO_ID) repoId: String
    ): RepoDetailsDto

    @GET("repos/{ownerName}/{repositoryName}/readme")
    suspend fun getRepositoryReadme(
        @Path(PATH_PARAM_OWNER) ownerName: String,
        @Path(PATH_PARAM_REPOSITORY) repositoryName: String,
        @Query(QUERY_PARAM_BRANCH) branchName: String
    ): JsonObject

    @GET("user")
    suspend fun signIn(
        @Header(HEADER_PARAM_TOKEN) token: String
    ): Response<UserInfoDto>

    companion object {
        private const val HEADER_PARAM_TOKEN = "Authorization"

        private const val PATH_PARAM_REPO_ID = "repoId"
        private const val PATH_PARAM_OWNER = "ownerName"
        private const val PATH_PARAM_REPOSITORY = "repositoryName"

        private const val QUERY_PARAM_BRANCH = "ref"
    }
}