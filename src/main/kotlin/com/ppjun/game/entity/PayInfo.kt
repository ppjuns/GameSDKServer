package com.ppjun.game.entity

data class PayInfo(val oId: Int,
                   val payWay: String,
                   val createTime: String,
                   val payTime: String,
                   val tradeNo: String,
                   val productPrice: String,
                   val payPrice: String,
                   val productName: String,
                   val userId: String,
                   val gameId: String,
                   val payStatus: String,
                   val gameOrderNo: String
)