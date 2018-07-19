package com.ppjun.game.service.mapper

import com.ppjun.game.entity.AdminInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface AdminInfoMapper {

    fun  getAdminByName(userName: String): List<AdminInfo>
    fun  getAdminByToken(token: String): List<AdminInfo>
}