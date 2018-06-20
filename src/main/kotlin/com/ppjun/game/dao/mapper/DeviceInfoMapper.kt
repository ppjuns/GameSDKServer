package com.ppjun.game.dao.mapper

import com.ppjun.game.entity.DeviceInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface DeviceInfoMapper {
    fun insertDevice(device:DeviceInfo)
    fun getDeviceByIMEI(imei:String):List<DeviceInfo>
}