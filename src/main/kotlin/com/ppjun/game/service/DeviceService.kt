package com.ppjun.game.service

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.ppjun.game.base.Constant
import com.ppjun.game.service.mapper.DeviceInfoMapper
import com.ppjun.game.entity.DeviceInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DeviceService : DeviceInfoMapper {
    override fun getDeviceById(gameId: String): List<DeviceInfo> {
        return deviceInfoMapper.getDeviceById(gameId)
    }

    fun getDeviceByIdByPage(page: Int, gameId: String): Pair<List<DeviceInfo>, Int> {
        PageHelper.startPage<DeviceInfo>(page, Constant.PAGE_SIZE)
        val devices = deviceInfoMapper.getDeviceById(gameId)
        val deviceInfos = PageInfo<DeviceInfo>(devices)

        return Pair(deviceInfos.list, deviceInfos.pages)
    }

    override fun getDeviceByIMEI(appId: String, imei: String): List<DeviceInfo> {
        return deviceInfoMapper.getDeviceByIMEI(appId, imei)
    }


    @Autowired
    lateinit var deviceInfoMapper: DeviceInfoMapper

    override fun insertDevice(device: DeviceInfo) {
        deviceInfoMapper.insertDevice(device)
    }
}