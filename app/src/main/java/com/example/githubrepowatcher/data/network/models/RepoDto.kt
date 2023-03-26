package com.example.githubrepowatcher.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    @SerialName("name")
    val name: String,
    @SerialName("language")
    val language: String?,
    @SerialName("description")
    val description: String?
)