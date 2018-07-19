package com.ppjun.game.service

import com.ppjun.game.entity.PayInfo
import com.ppjun.game.service.mapper.PayInfoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PayService : PayInfoMapper {
    override fun getProductName(orderNo: String): List<PayInfo> {
        return payInfoMapper.getProductName(orderNo)
    }

    @Autowired
    lateinit var payInfoMapper: PayInfoMapper

    override fun createOrder(payInfo: PayInfo) {
        return payInfoMapper.createOrder(payInfo)
    }
}