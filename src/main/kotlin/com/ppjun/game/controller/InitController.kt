package com.ppjun.game.controller


import com.ppjun.game.base.Constant.Companion.ERROR_CODE
import com.ppjun.game.base.Constant.Companion.ERROR_INIT
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.base.Constant.Companion.SUCCESS_INIT
import com.ppjun.game.entity.Response
import com.ppjun.game.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableAutoConfiguration
class InitController {


    @Autowired
    lateinit var gameService: GameService


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
}