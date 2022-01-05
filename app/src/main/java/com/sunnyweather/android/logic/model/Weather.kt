package com.sunnyweather.android.logic.model

data class Weather(val now: RealtimeResponse.Now, val daily: List<DailyResponse.DailyInfo>)