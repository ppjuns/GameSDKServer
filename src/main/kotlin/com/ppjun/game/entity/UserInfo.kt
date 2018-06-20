package com.ppjun.game.entity

data class UserInfo(val userName: String,
                    val userImg: String,
                    val userToken: String,
                    val userLevel: String,
                    val userSex: String,
                    val userOpenId: String,
                    val gameAppId:String,
                    val gameInfo: GameInfo)
//关联