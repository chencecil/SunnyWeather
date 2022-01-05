package com.sunnyweather.android.logic.model

import java.util.*

data class DailyResponse(val code: String, val daily: List<DailyInfo>) {
    data class DailyInfo(val tempMax: Int, val tempMin: Int, val iconDay: Int, val iconNight: Int,
                         val textDay: String, val textNight: String, val moonPhase: String,
                         val moonPhaseIcon: Int, val fxDate: String)
}

