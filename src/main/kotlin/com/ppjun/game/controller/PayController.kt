package com.ppjun.game.controller

import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.entity.Response
import com.ppjun.game.http.PayService
import feign.Feign
import feign.gson.GsonDecoder


import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.POST

@RestController
@EnableAutoConfiguration
class PayController {


//不用if else 代码更加优化通俗
  //  https://api.mch.weixin.qq.com/pay/unifiedorder
    @PostMapping("/pay/wechat")
    fun payByWechat(@RequestParam map: HashMap<String, String>): Response {

        val payService = Feign.builder()
                .decoder(GsonDecoder())
                .target(PayService::class.java, "https://wxpay.wxutil.com/pub_v2/app/app_pay.php")
        val result = payService.wechatPay()
        return Response(SUCCESS_CODE, "请求成功", result)
    }
}


@PostMapping("/pay/order")
fun createOrder(@RequestParam map: HashMap<String, String>): Response {



    return Response(SUCCESS_CODE, "请求成功", "")
}