package com.sunnyweather.android.logic.model

import android.annotation.SuppressLint
import com.sunnyweather.android.R
import java.text.SimpleDateFormat

class Sky (val info: String, val icon: Int, val bg: Int)

@SuppressLint("SimpleDateFormat")
fun getSkyIcon(daily: DailyResponse.DailyInfo): Int {

    val skyIcon: Int = when {
        daily.textDay == "晴" -> R.drawable.bg_clear_day
        daily.textDay.contains("云") -> R.drawable.ic_partly_cloud_day
        daily.textDay == "阴" -> R.drawable.ic_cloudy
        daily.textDay.contains("小雨") || daily.textDay.contains("细雨") ->
            R.drawable.ic_light_rain
        daily.textDay.contains("中雨") -> R.drawable.ic_moderate_rain
        daily.textDay.contains("大雨") -> R.drawable.ic_heavy_rain
        daily.textDay.contains("暴雨") -> R.drawable.ic_storm_rain
        daily.iconDay == 308 || daily.iconDay == 313 || daily.iconDay == 399 ->
            R.drawable.ic_moderate_rain

        daily.textDay.contains("雨") && daily.textDay.contains("雪") ->
            R.drawable.ic_sleet

        daily.textDay.contains("小雪") -> R.drawable.ic_light_snow
        daily.textDay.contains("中雪") -> R.drawable.ic_moderate_snow
        daily.textDay.contains("大雪") -> R.drawable.ic_heavy_snow
        daily.textDay.contains("暴雪") -> R.drawable.ic_heavy_snow
        daily.textDay.contains("阵雪") -> R.drawable.ic_thunder_shower
        daily.iconDay == 407 || daily.iconDay == 457 || daily.iconDay == 499 ->
            R.drawable.ic_moderate_snow

        daily.textDay.contains("小雪") -> R.drawable.ic_light_snow

        daily.iconDay in 503..508
        -> R.drawable.ic_fog
        daily.iconDay == 500 || daily.iconDay == 501 || daily.iconDay == 502 ->
            R.drawable.ic_light_haze
        daily.iconDay == 509 || daily.iconDay == 511 || daily.iconDay == 514 ->
            R.drawable.ic_moderate_haze
        daily.iconDay in 500..501 -> R.drawable.ic_moderate_haze
        daily.iconDay == 510 || daily.iconDay == 512 || daily.iconDay == 513 ||
                daily.iconDay == 515 -> R.drawable.ic_heavy_haze
        else -> R.drawable.ic_clear_day
    }

    return skyIcon
}

@SuppressLint("SimpleDateFormat")
fun getSkyBg(now: RealtimeResponse.Now): Int {
    val skyBg: Int
    val isDay = when (SimpleDateFormat("HH").format(now.obsTime).toInt()) {
        in 6..17 -> true
        else -> false
    }

    skyBg = when {
        now.text == "晴" && isDay -> R.drawable.bg_clear_day
        now.text == "晴" && !isDay -> R.drawable.bg_clear_night
        now.text.contains("云") && isDay -> R.drawable.ic_partly_cloud_day
        now.text.contains("云") && !isDay -> R.drawable.ic_partly_cloud_night
        now.text == "阴" -> R.drawable.bg_cloudy
        now.icon in 300..399 -> R.drawable.bg_rain
        now.icon in 400..499 -> R.drawable.bg_snow
        now.icon in 500..599 -> R.drawable.bg_fog
        else -> R.drawable.bg_clear_day
    }

    return skyBg
}

