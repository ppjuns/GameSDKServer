package com.ppjun.game.service

import com.ppjun.game.dao.mapper.UserInfoMapper
import com.ppjun.game.entity.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService : UserInfoMapper {


    @Autowired
    lateinit var userInfoMapper: UserInfoMapper


    override fun getUserByOpenId(openId: String): List<UserInfo> {
        return userInfoMapper.getUserByOpenId(openId)
    }

    override fun insertUser(user: UserInfo) {
        userInfoMapper.insertUser(user)
    }

    override fun getToken(): String {
        return userInfoMapper.getToken()
    }
}