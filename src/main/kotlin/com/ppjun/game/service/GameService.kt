package com.ppjun.game.service

import com.ppjun.game.service.mapper.GameInfoMapper
import com.ppjun.game.entity.GameInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameService : GameInfoMapper {


    @Autowired
    lateinit var gameInfoMapper: GameInfoMapper

    override fun getGameById(appId: String): List<GameInfo> {
        return gameInfoMapper.getGameById(appId)
    }

    override fun getGameByName(name: String): List<GameInfo> {
        return gameInfoMapper.getGameByName(name)
    }

    override fun getAllGame(): List<GameInfo> {
        return gameInfoMapper.getAllGame()
    }

    override fun insertGame(game: GameInfo) {
        gameInfoMapper.insertGame(game)
    }

    override fun deleteGame(gId: String):Int {
       return  gameInfoMapper.deleteGame(gId)
    }

    override fun modifyGame(gId: String, newName: String, modifyTime: String):Int {
      return   gameInfoMapper.modifyGame(gId, newName, modifyTime)

    }


}
