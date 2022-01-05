package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlaceServiceCreator {
    private const val PLACE_BASE_URL = "https://geoapi.qweather.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(PLACE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}