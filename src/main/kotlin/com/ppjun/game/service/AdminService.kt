package com.ppjun.game.service

import com.ppjun.game.service.mapper.AdminInfoMapper
import com.ppjun.game.entity.AdminInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AdminService : AdminInfoMapper {
    override fun getAdminByToken(token: String): List<AdminInfo> {
        return adminInfoMapper.getAdminByToken(token)
    }

    @Autowired
    lateinit var adminInfoMapper: AdminInfoMapper

    override fun getAdminByName(userName: String): List<AdminInfo> {
        return adminInfoMapper.getAdminByName(userName)
    }
}