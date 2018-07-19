package com.ppjun.game.http

import com.ppjun.game.entity.pay.Prepay
import feign.QueryMap
import feign.RequestLine


interface PayServiceImpl {

    @RequestLine("POST")
    fun wechatPay(@QueryMap map:HashMap<String,String>): Prepay

}