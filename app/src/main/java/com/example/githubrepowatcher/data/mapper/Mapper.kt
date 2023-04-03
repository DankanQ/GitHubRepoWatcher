package com.example.githubrepowatcher.data.mapper

import com.example.githubrepowatcher.data.network.models.UserInfoDto
import com.example.githubrepowatcher.domain.models.UserInfo
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun mapUserInfoDtoToModel(userInfoDto: UserInfoDto) = UserInfo(
        login = userInfoDto.login
    )
}