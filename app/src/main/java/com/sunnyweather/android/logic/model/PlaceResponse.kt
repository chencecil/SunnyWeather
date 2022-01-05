package com.sunnyweather.android.logic.model

data class PlaceResponse(val code: String, val location: List<Place>)
data class Place(val name: String, val lat: String, val lon: String, val adm1: String,
                 val country: String)
data class Location(val lng: String, val lat: String)