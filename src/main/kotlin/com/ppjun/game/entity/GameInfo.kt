package com.ppjun.game.entity

data class GameInfo(val gameName:String,
                    val appId :String,
                    val appKey:String,
                    val users:List<UserInfo>,
                    val devices:List<DeviceInfo>)