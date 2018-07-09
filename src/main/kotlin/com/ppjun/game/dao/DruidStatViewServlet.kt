package com.ppjun.game.dao

import com.alibaba.druid.support.http.StatViewServlet
import javax.servlet.annotation.WebInitParam
import javax.servlet.annotation.WebServlet


@WebServlet(urlPatterns = ["/druid/*"], initParams = [(WebInitParam(name = "allow", value = "")), (WebInitParam(name = "deny", value = "192.168.16.111")), (WebInitParam(name = "loginUsername", value = "admin")), (WebInitParam(name = "loginPassword", value = "admin")), (WebInitParam(name = "resetEnable", value = "true"))])
class DruidStatViewServlet : StatViewServlet() {
    companion object {
        private val serialVersionUID = 2359758657306626394L
    }

}