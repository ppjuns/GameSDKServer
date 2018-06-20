package com.ppjun.game.controller

import com.ppjun.game.base.Constant.Companion.ERROR_CODE
import com.ppjun.game.base.Constant.Companion.ERROR_IMG_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_NAME_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_OPENID_LOGIN
import com.ppjun.game.base.Constant.Companion.ERROR_SEX_LOGIN
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.base.Constant.Companion.SUCCESS_LOGIN
import com.ppjun.game.entity.Response
import com.ppjun.game.entity.UserInfo
import com.ppjun.game.service.UserService
import org.apache.tomcat.util.security.MD5Encoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sun.security.provider.MD5


@RestController
@EnableAutoConfiguration
class UserController {

    @Autowired
    lateinit var userService: UserService


    /**
     * 第三方登录
     */
    @PostMapping("/login")
    fun login(@RequestParam map: HashMap<String, String>): Response {
        val openId = map["open_id"]
        val userName = map["user_name"]
        val userImg = map["user_img"]
        val userSex = map["user_sex"]

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
        //查询user表 ，没有就创建，有就返回
        val userList=userService.getUserByOpenId(requireNotNull(openId))
        return if (userList.isEmpty()) {
            //insert
          val token=  MD5Encoder.encode((openId + System.currentTimeMillis().toString()).toByteArray())
            userService.insertUser(UserInfo(requireNotNull(userName), requireNotNull(userImg)
                    , token, "0", requireNotNull(userSex), requireNotNull(openId)))
           val newUserList=userService.getUserByOpenId(requireNotNull(openId))
            Response(SUCCESS_CODE, SUCCESS_LOGIN, newUserList[0])
        } else {
            //return
            Response(SUCCESS_CODE, SUCCESS_LOGIN, userList[0])
        }

    }

}