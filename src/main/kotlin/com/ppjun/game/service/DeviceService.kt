package com.ppjun.game.service

import com.ppjun.game.dao.mapper.DeviceInfoMapper
import com.ppjun.game.entity.DeviceInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class DeviceService :DeviceInfoMapper {
    override fun getDeviceByIMEI(appId:String,imei: String): List<DeviceInfo> {
        return deviceInfoMapper.getDeviceByIMEI(appId,imei)
    }


    @Autowired
    lateinit var deviceInfoMapper: DeviceInfoMapper
    override fun insertDevice(device: DeviceInfo) {
        deviceInfoMapper.insertDevice(device)

    }
}