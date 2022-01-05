package com.sunnyweather.android.logic.model

class Life (var cold: String, var dress: String, var ultraviolet_rays: String, var wash_car: String)

fun getLife(now: RealtimeResponse.Now): Life {
    val cold: String = when {
        now.temp < 10 -> "极易发"
        now.temp in 10..20 -> "易发"
        else -> "不易发"
    }
    val dress: String = when {
        now.temp < 10 -> "冷"
        now.temp in 10..20 -> "适中"
        else -> "暖"
    }
    val ultravioletRays: String = when {
        now.text.contains("晴") && now.temp > 20 -> "强"
        now.text.contains("晴") && now.temp in 10..20 -> "适中"
        else -> "弱"
    }
    val washCar: String = when {
        now.text.contains("雨") || now.text.contains("雪") -> "不宜"
        else -> "适宜"
    }

    return Life(cold, dress, ultravioletRays, washCar)
}