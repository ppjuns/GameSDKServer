package com.ppjun.game

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource


@Configuration
class DruidConfiguration {

    @Value("\${spring.datasource.url}")
    private lateinit var dbUrl: String
    @Value("\${spring.datasource.username}")
    private lateinit var username: String
    @Value("\${spring.datasource.password}")
    private lateinit var password: String
    @Value("\${spring.datasource.driver-class-name}")
    private lateinit var driverClassName: String


    @Bean
    @Primary
    fun datasource(): DataSource {
        val datasource = DruidDataSource()
        datasource.url = this.dbUrl
        datasource.username = username
        datasource.password = password
        datasource.driverClassName = driverClassName

        return datasource
    }
}