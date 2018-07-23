package com.ppjun.game.controller

import com.ppjun.game.base.Constant
import com.ppjun.game.entity.DeviceInfo
import com.ppjun.game.entity.Response
import com.ppjun.game.service.AdminService
import com.ppjun.game.service.DeviceService
import com.ppjun.game.service.GameService
import com.ppjun.game.util.TimeUtil.Companion.getCurrentTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@EnableAutoConfiguration
class DeviceController {
    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var deviceService: DeviceService


    @Autowired
    lateinit var adminService: AdminService
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
            return Response(Constant.ERROR_CODE, "imei 不能为空", "")
        }
        if (platform.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "platform 不能为空", "")
        }
        if (model.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "model 不能为空", "")
        }
        if (brand.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "brand 不能为空", "")
        }
        val gameList = gameService.getGameById(appId!!)
        if (gameList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "没找到对应游戏", "")
        }
        //先查询再添加
        val deviceServiceList = deviceService.getDeviceByIMEI(appId, imei!!)

        return if (deviceServiceList.isEmpty()) {
            deviceService.insertDevice(DeviceInfo(1, platform!!, model!!, brand!!, imei,
                    getCurrentTime(), 1.toString()))
            Response(Constant.SUCCESS_CODE, "添加成功", "")
        } else {
            Response(Constant.SUCCESS_CODE, "已添加", "")
        }

    }


    /**
     * 根据游戏id 查找设备
     */
    @PostMapping("/device")
    fun getDevice(@RequestParam map: HashMap<String, String>): Response {


        val token = map["user_token"]
        val gameId = map["game_id"]
        if (token.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "token 为空", "")
        }
        if (gameId.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "gameId 为空", "")
        }
        val adminList = adminService.getAdminByToken(token!!)
        if (adminList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "找不到游戏", "")
        }

        val deviceList= deviceService.getDeviceById(requireNotNull(gameId))

        return Response(Constant.SUCCESS_CODE, "获取成功", deviceList)
    }
    /**
     * 根据游戏id 查找设备，分页
     */
    @PostMapping("/device/page")
    fun getDeviceByPage(@RequestParam map: HashMap<String, String>): Response {
        val token = map["user_token"]
        val gameId = map["game_id"]
        val page = map["page"]
        if (token.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "token 为空", "")
        }
        if (gameId.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "gameId 为空", "")
        }
        if (page.isNullOrEmpty()) {
            return Response(Constant.ERROR_CODE, "page 为空", "")
        }
        val adminList = adminService.getAdminByToken(token!!)
        if (adminList.isEmpty()) {
            return Response(Constant.ERROR_CODE, "找不到游戏", "")
        }

        val deviceListPair= deviceService.getDeviceByIdByPage(page!!.toInt(),requireNotNull(gameId))

        val deviceMap=HashMap<String,Any>()
        deviceMap["list"]= deviceListPair.first
        deviceMap["page"]= deviceListPair.second
        return Response(Constant.SUCCESS_CODE, "获取成功", deviceMap)
    }

    fun deleteDeviceByGameId(gameId:String){
        deviceService.deleteDeviceByGameId(gameId)
    }

}