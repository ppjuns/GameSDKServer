package com.ppjun.game.service

import com.github.pagehelper.Constant
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.ppjun.game.base.Constant.Companion.PAGE_SIZE
import com.ppjun.game.entity.PayInfo
import com.ppjun.game.service.mapper.PayInfoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PayService : PayInfoMapper {
    override fun getPayInfo(gameId: String) :List<PayInfo>{
         return payInfoMapper.getPayInfo(gameId)
    }

    fun getPayInfoByGameIdByPage(page: Int, gameId: String) :Pair<List<PayInfo>,Int>{
        PageHelper.startPage<PayInfo>(page, PAGE_SIZE)
        val payList = payInfoMapper.getPayInfo(gameId)
        val pay = PageInfo<PayInfo>(payList)
        return Pair(pay.list,pay.pages)
    }

    override fun deletePayInfoByGameId(gameId: String) {
        return payInfoMapper.deletePayInfoByGameId(gameId)
    }

    //订单号获取商品名
    override fun getProductName(orderNo: String): List<PayInfo> {
        return payInfoMapper.getProductName(orderNo)
    }

    @Autowired
    lateinit var payInfoMapper: PayInfoMapper

    //创建订单
    override fun createOrder(payInfo: PayInfo) {
        return payInfoMapper.createOrder(payInfo)
    }
}