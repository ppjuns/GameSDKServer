package com.ppjun.game.dao.mapper

import com.ppjun.game.entity.UserInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface UserInfoMapper {

    fun getUserByOpenId(openId: String):List<UserInfo>
    fun insertUser(user: UserInfo)
    fun getToken(): String
}