package com.sunnyweather.android.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.*
import com.sunnyweather.android.logic.model.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    lateinit var mBinding : ActivityWeatherBinding

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }
    private fun showWeatherInfo(weather: Weather) {
        Log.d("cclog", "now: ${weather.now}")
        Log.d("cclog", "daily: ${weather.daily}")

        mBinding.includeNow.placeName.text = viewModel.placeName
        val now = weather.now
        val daily = weather.daily
        // 填充now.xml布局中的数据
        val currentTempText = "${now.temp} ℃"
        mBinding.includeNow.currentTemp.text = currentTempText
        mBinding.includeNow.currentSky.text = now.text
        val currentPM25Text = "风向 ${now.windDir}"
        mBinding.includeNow.currentAQI.text = currentPM25Text
        mBinding.includeNow.nowLayout.setBackgroundResource(getSkyBg(now))
        // 填充forecast.xml布局中的数据
        mBinding.includeForecast.forecastLayout.removeAllViews()
        val days = daily.size
        for (i in 0 until days) {
            val info = daily[i]


            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item,
                mBinding.includeForecast.forecastLayout, false)
            val forecastItemBinding = ForecastItemBinding.bind(view)

            forecastItemBinding.dateInfo.text = info.fxDate
            forecastItemBinding.skyIcon.setImageResource(getSkyIcon(info))
            val weatherText = "${info.textDay} ~ ${info.textNight}"
            forecastItemBinding.skyInfo.text = weatherText
            val tempText = "${info.tempMin} ~ ${info.tempMax} ℃"
            forecastItemBinding.temperatureInfo.text = tempText
            mBinding.includeForecast.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeInfo = getLife(weather.now)
        mBinding.includeLifeIndex.coldRiskText.text = lifeInfo.cold
        mBinding.includeLifeIndex.dressingText.text = lifeInfo.dress
        mBinding.includeLifeIndex.ultravioletText.text = lifeInfo.ultraviolet_rays
        mBinding.includeLifeIndex.carWashingText.text = lifeInfo.wash_car
        mBinding.weatherLayout.visibility = View.VISIBLE
    }
}