package com.ppjun.game.dao.mapper

import com.ppjun.game.entity.DeviceInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface DeviceInfoMapper {
    fun insertDevice(device:DeviceInfo)
    fun getDeviceByIMEI(appId:String,imei:String):List<DeviceInfo>
    fun getDeviceById(gameId:String):List<DeviceInfo>

}