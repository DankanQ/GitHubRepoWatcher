package com.example.githubrepowatcher.domain.models

data class RepoDetails(
    val url: String,
    val license: License,
    val stars: Int,
    val forks: Int,
    val subscribers: Int
) {
    data class License(
        val spdxId: String
    )
}