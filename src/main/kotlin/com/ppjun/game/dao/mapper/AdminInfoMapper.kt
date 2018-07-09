package com.ppjun.game.dao.mapper

import com.ppjun.game.entity.AdminInfo
import com.ppjun.game.entity.UserInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface AdminInfoMapper {

    fun  getAdminByName(userName: String): List<AdminInfo>
    fun  getAdminByToken(token: String): List<AdminInfo>
}