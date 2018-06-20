package com.ppjun.game.controller


import com.ppjun.game.base.Constant.Companion.ERROR_CODE
import com.ppjun.game.base.Constant.Companion.ERROR_INIT
import com.ppjun.game.base.Constant.Companion.ERROR_REPECT_ADD
import com.ppjun.game.base.Constant.Companion.SUCCESS_ADD
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.base.Constant.Companion.SUCCESS_INIT
import com.ppjun.game.entity.DeviceInfo
import com.ppjun.game.entity.GameInfo
import com.ppjun.game.entity.Response
import com.ppjun.game.service.DeviceService
import com.ppjun.game.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
@EnableAutoConfiguration
class InitController {

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var deviceService: DeviceService

    @RequestMapping("/")
    fun index(): String {
        return "Hello World"
    }


    /**
     * SDK 初始化接口，只支持post
     */


    @PostMapping("/init")
    fun initSDK(@RequestParam map: HashMap<String, String>)
            : Response {
        val appId = map["app_id"]
        val appKey = map["app_key"]
        if (appId.isNullOrEmpty()) {
            return Response(ERROR_CODE, "appId 不能为空", "")
        }
        if (appKey.isNullOrEmpty()) {
            return Response(ERROR_CODE, "appKey 不能为空", "")
        }

        val games = gameService.getGameById(requireNotNull(appId))
        return if (games.isNotEmpty() && games[0].appId == appId && games[0].appKey == appKey) {
            Response(SUCCESS_CODE, SUCCESS_INIT, "")
        } else {
            Response(ERROR_CODE, ERROR_INIT, "")
        }

    }

    @PostMapping("/addgame")
    fun addGame(@RequestParam map: HashMap<String, String>)
            : Response {
        val gameName = map["game_name"]
        val appId = map["app_id"]
        val appKey = map["app_key"]

        if (gameName.isNullOrEmpty()) {
            return Response(ERROR_CODE, "gameName 不能为空", "")
        }
        if (appId.isNullOrEmpty()) {
            return Response(ERROR_CODE, "appId 不能为空", "")
        }
        if (appKey.isNullOrEmpty()) {
            return Response(ERROR_CODE, "appKey 不能为空", "")
        }
        if (gameService.getGameByName(requireNotNull(gameName)).isNotEmpty()) {
            return Response(ERROR_CODE, ERROR_REPECT_ADD, "")
        }
        gameService.insertGame(GameInfo(requireNotNull(gameName), requireNotNull(appId),
                requireNotNull(appKey)))

        return Response(SUCCESS_CODE, SUCCESS_ADD, "")
    }

    @GetMapping("/error")
    fun error(@RequestParam map: HashMap<String, String>): Response {
        return Response(-200, "error", "")
    }


    @PostMapping("/device")
    fun addDevice(@RequestParam map: HashMap<String, String>): Response {


        val imei = map["device_imei"]
        val platform = map["device_platform"]
        val model = map["device_model"]
        val brand = map["device_brand"]


        if (imei.isNullOrEmpty()) {
            return Response(ERROR_CODE, "imei 不能为空", "")
        }
        if (platform.isNullOrEmpty()) {
            return Response(ERROR_CODE, "platform 不能为空", "")
        }
        if (model.isNullOrEmpty()) {
            return Response(ERROR_CODE, "model 不能为空", "")
        }
        if (brand.isNullOrEmpty()) {
            return Response(ERROR_CODE, "brand 不能为空", "")
        }

        //先查询在添加
        val deviceServiceList = deviceService.getDeviceByIMEI(imei!!)

        if (deviceServiceList.isEmpty()) {

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sdf.format(System.currentTimeMillis())
            deviceService.insertDevice(DeviceInfo(platform!!, model!!, brand!!, imei,
                    date))

        }
        return Response(SUCCESS_CODE, "添加成功", "")
    }



}