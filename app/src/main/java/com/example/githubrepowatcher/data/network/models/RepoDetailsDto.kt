package com.example.githubrepowatcher.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetailsDto(
    @SerialName("html_url")
    val url: String,
    @SerialName("license")
    val license: License?,
    @SerialName("stargazers_count")
    val stars: Int,
    @SerialName("forks")
    val forks: Int,
    @SerialName("subscribers_count")
    val subscribers: Int
) {
    @Serializable
    data class License(
        @SerialName("spdx_id")
        val spdxId: String
    )
}