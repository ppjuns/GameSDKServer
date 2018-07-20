package com.ppjun.game.controller


import com.alipay.api.DefaultAlipayClient
import com.alipay.api.domain.AlipayTradeAppPayModel
import com.alipay.api.internal.util.AlipaySignature
import com.alipay.api.request.AlipayTradeAppPayRequest
import com.ppjun.game.base.Constant
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.base.Constant.Companion.WECHAT_MCH_KEY
import com.ppjun.game.entity.OrderInfo
import com.ppjun.game.entity.PayInfo
import com.ppjun.game.entity.Response
import com.ppjun.game.http.HttpApi.Companion.WECHAT_PAY_URL
import com.ppjun.game.http.PayServiceImpl
import com.ppjun.game.service.GameService
import com.ppjun.game.service.PayService
import com.ppjun.game.service.UserService
import com.ppjun.game.util.MD5Util
import com.ppjun.game.util.RsaUtil
import com.ppjun.game.util.TimeUtil

import feign.Feign
import feign.gson.GsonDecoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.HashMap

@RestController
@EnableAutoConfiguration
class PayController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var payService: PayService

    lateinit var request: AlipayTradeAppPayRequest

    /**
     * 微信支付
     */
    //不用if else 代码更加优化通俗
    //  https://api.mch.weixin.qq.com/pay/unifiedorder
    @PostMapping("/pay/wechat")
    fun payByWechat(@RequestParam map: HashMap<String, String>): Response {
        // 拿到订单号查询，查询商品名，价格
        val orderNo = map["order_no"]
        if (orderNo.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "orderNo 为空", "")
        }

        val time = TimeUtil.getCurrentTime()
        val payList = payService.getProductName(orderNo!!)
        if (payList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "订单不存在", "")
        }

        val signMap = TreeMap<String, String>()

        signMap["appid"] = Constant.WECHAT_APP_ID
        signMap["mch_id"] = Constant.WECHAT_MCH_ID
        signMap["nonce_str"] = time
        signMap["body"] = payList[0].productName
        signMap["out_trade_no"] = payList[0].gameOrderNo
        signMap["total_fee"] = payList[0].productPrice
        signMap["notify_url"] = "/pay/wechat/notify"
        signMap["trade_type"] = Constant.TRADE_TYPE

        val stringA = StringBuilder()
        val entrys = signMap.entries
        for (entry in entrys) {
            stringA.append(entry.key + "=" + entry.value + "&")
        }
        val stringSignTemp = stringA.toString() + "key=" + WECHAT_MCH_KEY
        val sign = MD5Util.getMD5(stringSignTemp).toUpperCase()
        val paramsMap = HashMap<String, String>()
        paramsMap["appid"] = Constant.WECHAT_APP_ID
        paramsMap["mch_id"] = Constant.WECHAT_MCH_ID
        paramsMap["nonce_str"] = time
        paramsMap["sign"] = sign
        paramsMap["body"] = payList[0].productName
        paramsMap["out_trade_no"] = payList[0].gameOrderNo
        paramsMap["total_fee"] = payList[0].productPrice
        paramsMap["notify_url"] = "/pay/wechat/notify"
        paramsMap["trade_type"] = Constant.TRADE_TYPE

        val payService = Feign.builder()
                .decoder(GsonDecoder())
                .target(PayServiceImpl::class.java, WECHAT_PAY_URL)
        val result = payService.wechatPay(paramsMap)
        return Response(SUCCESS_CODE, "请求成功", result)
    }


    /**
     * sdk内订单,单位分 两位小数0.01
     */
    @PostMapping("/pay/order")
    fun createOrder(@RequestParam map: HashMap<String, String>): Response {
        val productName = map["product_name"]
        val productPrice = map["product_price"]
        val appId = map["app_id"]
        val token = map["user_token"]

        if (token.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "token 为空", "")
        }

        if (appId.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "appId 为空", "")
        }

        if (productName.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "productName 为空", "")
        }
        if (productPrice.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "productPrice 为空", "")
        }


        val gameList = gameService.getGameById(appId!!)

        if (gameList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "没找到对应游戏", "")
        }
        val userList = userService.getUserByToken(appId, token!!)
        if (userList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "token 已失效，重新登录", "")
        }


        //userId, gameId, productName, productPrice, gameOrderNo, createTime
        //订单号以当前时间+uid+商品名为原型,再md5
        val uId = userList[0].uId.toString()
        val gameOrderNo = MD5Util.getMD5(System.currentTimeMillis().toString() + uId + productName)

        val payInfo = PayInfo(uId, gameList[0].gId.toString(),
                productName!!, productPrice!!, gameOrderNo, TimeUtil.getCurrentTime())
        payService.createOrder(payInfo)
        return Response(SUCCESS_CODE, "请求成功", OrderInfo(gameOrderNo, productName, productPrice))
    }


    /**
     * 微信支付回调地址
     */
    @PostMapping("/pay/wechat/notify")
    fun wechatNotify(@RequestParam map: HashMap<String, String>): String {
        return "wechat/notify"
    }

    /**
     * 支付宝支付回调地址，服务器通知我们
     */
    @PostMapping("/pay/alipay/notify")
    fun alipayNotify(@RequestParam map: HashMap<String, String>): String {
        return "alipay/notify"
    }

    @PostMapping("/pay/alipay")
    fun payByAlipay(@RequestParam map: HashMap<String, String>): Response {

        // 拿到订单号查询，查询商品名，价格
        val orderNo = map["order_no"]
        if (orderNo.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "orderNo 为空", "")
        }

        val time = TimeUtil.getCurrentTime()
        val payList = payService.getProductName(orderNo!!)
        if (payList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "订单不存在", "")
        }

        val notifyUrl = "/pay/alipay/notify"
        val client = DefaultAlipayClient(notifyUrl, Constant.ALIPAY_APP_ID, Constant.ALIPAY_PRIVATE_KEY
                , "json", "utf-8", Constant.ALIPAY_PUBLIC_KEY, "RSA2")
        request = AlipayTradeAppPayRequest()
        val model = AlipayTradeAppPayModel()
        model.body = payList[0].productName
        model.outTradeNo = payList[0].gameOrderNo
        model.timeoutExpress = "30m"
        model.totalAmount = payList[0].productPrice
        model.productCode = "QUICK_MESECURITY_PAY"
        request.bizModel = model
        request.notifyUrl = notifyUrl
        val response = client.sdkExecute(request)
        return Response(SUCCESS_CODE, "请求成功", response)
    }

    @PostMapping("/pay/rsakey")
    fun generateRsaKey(@RequestParam map: HashMap<String, String>): Response {
        val secret = RsaUtil.encryptDataByPrivateKey("nainai".toByteArray(), RsaUtil.keyStrToPrivate(Constant.PRIVATE_KEY)!!)
        val artcle = RsaUtil.decryptDataByPublicKey(secret, RsaUtil.keyStrToPublicKey(Constant.PUBLIC_KEY)!!)
        return Response(SUCCESS_CODE, secret, String(artcle!!))
    }


    /**
     * 微信验证
     */

    @PostMapping("/pay/wechat/check")
    fun wechatCheck() {

        val str = arrayOf(arrayOf("1"), arrayOf("1"))

    }

    /**
     * 支付宝验证，我们主动去问支付宝
     */

    @PostMapping("/pay/alipay/check")
    fun alipayCheck() {

        val params = HashMap<String, String>()
        val requestMap = request.textParams.entries
        for (param in requestMap) {
            val a = arrayOf("a")
            val values = param.value as Array<String>
            val valueStr = StringBuilder()

            for ((i, value) in (0 until values.size).withIndex()) {

                if (i == values.size - 1) {
                    valueStr.append(values[i])
                } else {
                    valueStr.append(values[i] + ",")
                }

            }
            params[param.key] = valueStr.toString()
        }

        val flag = AlipaySignature.rsaCheckV1(params, Constant.ALIPAY_PUBLIC_KEY, "utf-8", "RSA2")
    }


}
