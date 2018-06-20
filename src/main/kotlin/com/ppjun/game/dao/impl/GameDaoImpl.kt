package com.ppjun.game.dao.impl

import com.ppjun.game.dao.GameDao
import com.ppjun.game.entity.GameInfo
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory


//用了Mapper就不用impl
class GameDaoImpl constructor(var sqlSessionFactory: DefaultSqlSessionFactory) : GameDao {
    override fun getGameByName(name: String): GameInfo {
        val sqlSession = sqlSessionFactory.openSession()
        val game = sqlSession.selectOne<GameInfo>("test.getGameByName", name)
        sqlSession.close()
        return game
    }


    override fun getGame(appid: String): GameInfo {
        val sqlSession = sqlSessionFactory.openSession()
        val game = sqlSession.selectOne<GameInfo>("test.getGame", appid)
        sqlSession.close()
        return game
    }

    override fun insert(game: GameInfo) {
        val sqlSession = sqlSessionFactory.openSession()
        sqlSession.selectOne<GameInfo>("test.insertGame", game)
        sqlSession.commit()
        sqlSession.close()
    }
}