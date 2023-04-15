package com.example.githubrepowatcher.data.mapper

import com.example.githubrepowatcher.data.network.models.RepoDto
import com.example.githubrepowatcher.data.network.models.UserInfoDto
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.models.UserInfo
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun mapUserInfoDtoToModel(userInfoDto: UserInfoDto) = UserInfo(
        login = userInfoDto.login
    )

    fun mapRepoDtoToModel(repoDto: RepoDto) = Repo(
        name = repoDto.name,
        language = repoDto.language ?: EMPTY_STRING_VALUE,
        description = repoDto.description ?: EMPTY_STRING_VALUE
    )

    companion object {
        private const val EMPTY_STRING_VALUE = ""
    }
}