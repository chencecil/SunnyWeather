package com.sunnyweather.android.logic.model

import java.util.*

data class RealtimeResponse(val code: String, val now: Now) {
    data class Now(val icon: Int, val temp: Int, val text: String, val windDir: String,
                      val obsTime: Date)
}