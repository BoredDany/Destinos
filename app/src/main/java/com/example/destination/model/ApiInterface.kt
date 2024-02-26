package com.example.destination.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("current.json")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("key") apiKey: String,
        @Query("aqi") aqi: String = "no" // Include additional query parameter
    ): Call<WeatherApp>
}