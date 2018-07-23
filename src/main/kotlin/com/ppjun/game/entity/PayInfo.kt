package com.ppjun.game.entity

//userId, gameId, productName, productPrice, gameOrderNo, createTime
data class PayInfo(
        val userId: String = "",
        val gameId: String = "",
        val productName: String = "",
        val productPrice: String = "",
        val gameOrderNo: String = "",
        val createTime: String = "",
        val pId: Int = 0,
        val payWay: String = "",
        val payTime: String = "",
        val payTradeNo: String = "",
        val payPrice: String = "",
        val payStatus: String = ""
)