package com.ppjun.game.controller

import com.ppjun.game.base.Constant
import com.ppjun.game.base.Constant.Companion.ERROR_CODE
import com.ppjun.game.base.Constant.Companion.ERROR_IMG_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_NAME_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_OPENID_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_SEX_LOGIN
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.base.Constant.Companion.SUCCESS_LOGIN
import com.ppjun.game.entity.Response
import com.ppjun.game.entity.UserInfo
import com.ppjun.game.service.AdminService
import com.ppjun.game.service.GameService
import com.ppjun.game.service.UserService
import com.ppjun.game.util.MD5Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@EnableAutoConfiguration
class UserController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var adminService: AdminService

    /**
     * 第一次登陆成功，调用登陆接口，把用户写进数据库
     */
    @PostMapping("/login")
    fun login(@RequestParam map: HashMap<String, String>): Response {
        val openId = map["open_id"]
        val appId = map["app_id"]
        val userName = map["user_name"]
        val userImg = map["user_img"]
        val userSex = map["user_sex"]
        val platform = map["platform"]

        if (openId.isNullOrEmpty()) {
            return Response(ERROR_CODE, ERROR_OPENID_LOGIN, "")
        }
        if (userName.isNullOrEmpty()) {
            return Response(ERROR_CODE, ERROR_NAME_LOGIN, "")
        }
        if (userImg.isNullOrEmpty()) {
            return Response(ERROR_CODE, ERROR_IMG_LOGIN, "")
        }
        if (userSex.isNullOrEmpty()) {
            return Response(ERROR_CODE, ERROR_SEX_LOGIN, "")
        }
        if (platform.isNullOrEmpty()) {
            return Response(ERROR_CODE, "platform 为空", "")
        }
        val userList = userService.getUserByAppId(appId!!, openId!!)

        return if (userList.isEmpty()) {

            //add
            val gameList = gameService.getGameById(appId)

            if (gameList.isEmpty()) {
                return Response(ERROR_CODE, "没找到对应游戏", "")
            }


            val userToken = MD5Util.getMD5(System.currentTimeMillis().toString() + appId + userName + userImg)

            val user = UserInfo(1, userName!!, userImg!!, userToken,
                    "0", userSex!!, openId, gameList[0].gId.toString(), platform!!)
            userService.insertUser(user)
            Response(SUCCESS_CODE, SUCCESS_LOGIN, user)
        } else {

            //刷新token并返回
            val newToken = MD5Util.getMD5(System.currentTimeMillis().toString() + appId + userName + userImg)
            userService.updateUserToken(openId, newToken)
            val newUserList = userService.getUserByAppId(appId!!, openId!!)
            Response(SUCCESS_CODE, SUCCESS_LOGIN, newUserList[0])
        }

    }


    /**
     * 检测token是否有效
     */
    @PostMapping("/token/check")
    fun checkToken(@RequestParam map: HashMap<String, String>): Response {

        val token = map["user_token"]
        val appId = map["app_id"]

        if (token.isNullOrEmpty()) {
            return Response(ERROR_CODE, "token 为空", "")
        }

        if (appId.isNullOrEmpty()) {
            return Response(ERROR_CODE, "appId 为空", "")
        }

        val gameList = gameService.getGameById(appId!!)

        if (gameList.isEmpty()) {
            return Response(ERROR_CODE, "没找到对应游戏", "")
        }


        val userList = userService.getUserByToken(appId, token!!)

        return if (userList.isEmpty()) {
            Response(ERROR_CODE, "token 已失效，重新登录", "")

        } else {
            return Response(SUCCESS_CODE, "有效token", "")
        }
    }

    /**
     * 根据gameid
     * 获取用户列表
     */

    @PostMapping("/user")
    fun getUserByGameId(@RequestParam map: HashMap<String, String>): Response {

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
        val userList = userService.getUserByGameId(gameId!!)
        return Response(SUCCESS_CODE, "获取成功", userList)

    }
}