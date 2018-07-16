package com.ppjun.game.http

import com.ppjun.game.entity.pay.Prepay
import feign.RequestLine


interface PayService {

    @RequestLine("POST")
    fun wechatPay(): Prepay


}