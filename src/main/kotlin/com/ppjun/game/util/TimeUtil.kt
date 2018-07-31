package com.ppjun.game.util

import java.text.SimpleDateFormat

class TimeUtil{

    companion object {

        fun getCurrentTime(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return sdf.format(System.currentTimeMillis())
        }


        fun getOrderCurrentTime(): String {
            val sdf = SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
            return sdf.format(System.currentTimeMillis())
        }
    }
}