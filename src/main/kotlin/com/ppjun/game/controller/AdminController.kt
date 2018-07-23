package com.ppjun.game.controller

import com.ppjun.game.base.Constant.Companion.ERROR_CODE
import com.ppjun.game.base.Constant.Companion.SUCCESS_CODE
import com.ppjun.game.entity.Response
import com.ppjun.game.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@EnableAutoConfiguration
class AdminController {


    @Autowired
    lateinit var adminService: AdminService




    @GetMapping("/admin")
    fun admin(): Response {
        return Response(ERROR_CODE, "用户名不存在", "")
    }

    @PostMapping("/admin/login")
    fun login(@RequestParam map: HashMap<String, String>): Response {
        val userName = map["user_name"]
        val userPwd = map["user_pwd"]
        if (userName.isNullOrEmpty()) {
            return Response(ERROR_CODE, "userName 为空", "")
        }
        if (userPwd.isNullOrEmpty()) {
            return Response(ERROR_CODE, "userPwd 为空", "")
        }

        val adminList = adminService.getAdminByName(userName!!)
        return if (adminList.isNotEmpty()) {
            if (adminList[0].userPwd != userPwd) {
                return Response(ERROR_CODE, "密码不正确", "")
            }
            //success
            Response(SUCCESS_CODE, "登录成功", adminList[0])
        } else {
            //
            Response(ERROR_CODE, "用户名不存在", "")

        }


    }
}