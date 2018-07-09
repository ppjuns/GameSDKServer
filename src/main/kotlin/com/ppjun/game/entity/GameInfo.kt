package com.ppjun.game.entity

data class GameInfo(
      var gId: Int=0,
      var gameName: String="",
      var appId: String="",
      var appKey: String="",
      var gameTime:String="",
      var users: ArrayList<UserInfo> = arrayListOf(),
      var devices: ArrayList<DeviceInfo> = arrayListOf()
)