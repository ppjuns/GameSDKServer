package com.ppjun.game.base

import org.omg.PortableInterceptor.SUCCESSFUL

class Constant {
    companion object {
        val SUCCESS_CODE = 200
        val ERROR_CODE = -200
        val SUCCESS_INIT = "初始化成功"
        val ERROR_INIT="初始化失败"
        val SUCCESS_ADD = "新增成功"
        val ERROR_ADD = "新增失败"
        val ERROR_REPECT_ADD = "新增失败，游戏已存在"
        val ERROR_OPENID_LOGIN="登录失败,openId 为空"
        val ERROR_NAME_LOGIN="登录失败,name 为空"
        val ERROR_IMG_LOGIN="登录失败,img 为空"
        val ERROR_SEX_LOGIN="登录失败,sex 为空"
        val SUCCESS_LOGIN="登录成功"
    }
}