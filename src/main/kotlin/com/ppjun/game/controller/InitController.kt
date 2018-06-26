package com.ppjun.game.controller


import com.ppjun.game.GameApplication
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
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

@RestController
@EnableAutoConfiguration
class InitController {

    val logger = LoggerFactory.getLogger(GameApplication::class.java)
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


    @PostMapping("/game/add")
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
        gameService.insertGame(GameInfo(1, requireNotNull(gameName), requireNotNull(appId),
                requireNotNull(appKey)))

        return Response(SUCCESS_CODE, SUCCESS_ADD, "")
    }


    /**
     * 添加设备接口
     */

    @PostMapping("/device/init")
    fun addDevice(@RequestParam map: HashMap<String, String>): Response {

        val appId = map["app_id"]
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
        val gameList = gameService.getGameById(appId!!)
        if (gameList.isEmpty()) {
            return Response(ERROR_CODE, "没找到对应游戏", "")
        }
        //先查询再添加
        val deviceServiceList = deviceService.getDeviceByIMEI(appId, imei!!)

        return if (deviceServiceList.isEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sdf.format(System.currentTimeMillis())
            deviceService.insertDevice(DeviceInfo(1, platform!!, model!!, brand!!, imei,
                    date, gameList[0].gId.toString()))
            Response(SUCCESS_CODE, "添加成功", "")
        } else {
            Response(SUCCESS_CODE, "已添加", "")
        }

    }


    //查询有用户的游戏
    @PostMapping("/game")
    fun getGame(@RequestParam map: HashMap<String, String>): Response {
        val appId = map["app_id"]
        val game = gameService.getByAppId(appId!!)
        if (game != null) {
            logger.info(game.gameName)
            logger.info(game.users.size.toString())
        } else {

        }

        return Response(SUCCESS_CODE, "", "")
    }
}