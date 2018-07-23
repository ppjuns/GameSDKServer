package com.ppjun.game.service

import com.alibaba.druid.sql.PagerUtils
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.ppjun.game.base.Constant
import com.ppjun.game.service.mapper.UserInfoMapper
import com.ppjun.game.entity.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService : UserInfoMapper {
    override fun deleteUserByGameId(gameId: String) {
        return userInfoMapper.deleteUserByGameId(gameId)
    }

    override fun getUserByGameId(gameId: String): List<UserInfo> {
        return userInfoMapper.getUserByGameId(gameId)
    }

     fun getUserByGameIdByPage(page:Int,gameId: String): Pair<List<UserInfo>,Int> {
        PageHelper.startPage<UserInfo>(page, Constant.PAGE_SIZE)
         val userList=userInfoMapper.getUserByGameId(gameId)
        val userInfos= PageInfo<UserInfo>(userList)
        return Pair(userInfos.list,userInfos.pages)
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