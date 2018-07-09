package com.ppjun.game.dao

import com.alibaba.druid.support.http.WebStatFilter
import javax.servlet.annotation.WebFilter
import javax.servlet.annotation.WebInitParam


@WebFilter(filterName = "druidWebStatFilter", urlPatterns = arrayOf("/*"), initParams = arrayOf(WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")))// 忽略资源
class DruidStatFilter : WebStatFilter()