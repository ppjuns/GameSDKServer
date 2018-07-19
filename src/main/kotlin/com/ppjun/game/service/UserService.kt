package com.ppjun.game.service

import com.ppjun.game.service.mapper.UserInfoMapper
import com.ppjun.game.entity.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService : UserInfoMapper {
    override fun getUserByGameId(gameId: String): List<UserInfo> {
        return userInfoMapper.getUserByGameId(gameId)
    }

    override fun updateUserToken(openId: String, newToken: String) {
        return userInfoMapper.updateUserToken(openId,newToken)
    }


    override fun getUserByToken(appId: String, token: String): List<UserInfo> {
       return  userInfoMapper.getUserByToken(appId,token)
    }

    override fun getUserByAppId(appId: String,openId:String): List<UserInfo> {
        return userInfoMapper.getUserByAppId(appId,openId)
    }


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