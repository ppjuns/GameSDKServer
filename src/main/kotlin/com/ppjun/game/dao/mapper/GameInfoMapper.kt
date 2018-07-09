package com.ppjun.game.dao.mapper

import com.ppjun.game.entity.GameInfo
import com.ppjun.game.entity.UserInfo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GameInfoMapper {

    fun getGameById(appId: String): List<GameInfo>
    fun getGameByName(name: String): List<GameInfo>
    fun getAllGame(): List<GameInfo>
    fun insertGame(game: GameInfo)
    fun modifyGame(gId: String, newName: String, modifyTime: String)
    fun deleteGame(gId: String)

}