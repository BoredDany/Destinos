package com.example.destination

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.destination.model.ApiInterface
import com.example.destination.model.Destination
import com.example.destination.model.DestinationManager
import com.example.destination.model.WeatherApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecomendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendation)

        val title = findViewById<TextView>(R.id.title)
        val cityField = findViewById<TextView>(R.id.city)
        val info = findViewById<TextView>(R.id.info)

        if(DestinationManager.instance.favs.isEmpty()){
            title.text = "Recomendaciones: NA"
            cityField.text = ""
            info.text = ""
        }else{
            val city = DestinationManager.instance.buildRecomendations()
            val destination: Destination? = city?.let { DestinationManager.instance.getDestinationbyCity(it) }
            if (destination != null) {
                getWeather(city)
                cityField.text = destination.city
                info.text = destination.country + "\n" + destination.category + "\n" + destination.activity +  "\n$" + destination.price
            }
        }

    }

    fun getWeather(nombreQuery : String){
        val txtViewCiudadC = findViewById<TextView>(R.id.infoWeather)

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.weatherapi.com/v1/").build().create(ApiInterface::class.java)
        val response = retrofit.getWeatherData(nombreQuery, "f5857345c87c41f486b01214242502")
        response.enqueue(object : Callback<WeatherApp> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){

                    txtViewCiudadC.text = """
                        Location: ${responseBody.location.name}, ${responseBody.location.region}, ${responseBody.location.country}
                        Local Time: ${responseBody.location.localtime}
                        Current Temperature: ${responseBody.current.temp_c}°C
                        Feels Like: ${responseBody.current.feelslike_c}°C
                        """.trimIndent()
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                Log.i("FAIL", "Ha fallado")
                Log.e("API_RESPONSE", "Error fetching weather data", t)
            }

        })
    }
}