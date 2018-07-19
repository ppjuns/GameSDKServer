package com.ppjun.game.service.mapper

import com.ppjun.game.entity.PayInfo
import org.apache.ibatis.annotations.Mapper


@Mapper
interface PayInfoMapper {
    fun createOrder(payInfo:PayInfo)
    fun getProductName(orderNo:String):List<PayInfo>
}