package com.ppjun.game.dao

import com.ppjun.game.entity.GameInfo

interface GameDao {
    @Throws(Exception::class)
    fun getGame(appid:String):GameInfo


    @Throws(Exception::class)
    fun getGameByName(name:String):GameInfo

    @Throws(Exception::class)
    fun insert(gameInfo: GameInfo)


}