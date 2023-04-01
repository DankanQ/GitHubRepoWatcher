package com.example.githubrepowatcher.data.mapper

import com.example.githubrepowatcher.data.network.models.UserInfoDto
import com.example.githubrepowatcher.domain.models.UserInfo

class GitHubMapper {
    fun mapUserInfoDtoToModel(userInfoDto: UserInfoDto) = UserInfo(
        login = userInfoDto.login
    )
}