package com.ppjun.game.entity.pay

import com.google.gson.annotations.SerializedName

data class Prepay(@SerializedName("appid") val appId: String,
                  @SerializedName("partnerid") val partnerid: String,
                  @SerializedName("package") val packageName: String,
                  @SerializedName("noncestr") val nonceStr: String,
                  @SerializedName("timestamp") val timesTamp: String,
                  @SerializedName("prepayid") val prepayId: String,
                  @SerializedName("sign") val sign: String)