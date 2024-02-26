package com.example.destination

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.destination.model.Destination
import com.example.destination.model.DestinationManager
import com.example.destination.model.ApiInterface
import com.example.destination.model.WeatherApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val city = intent.getStringExtra("city")
        val cityField = findViewById<TextView>(R.id.city)
        val info = findViewById<TextView>(R.id.info)
        val favBtn = findViewById<Button>(R.id.favsBtn)
        val destination: Destination? = city?.let { DestinationManager.instance.getDestinationbyCity(it) }

        if (city != null) {
            getWeather(city)
        }
        initializeButtonStyle(destination, favBtn)
        initializeInfo(destination, cityField, info)

        favBtn.setOnClickListener {
            styleButton(favBtn)
            if(!DestinationManager.instance.isAddedToFav(destination)){
                DestinationManager.instance.addFav(destination)
                Toast.makeText(this,"añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun styleButton(btn: Button){
        btn.isEnabled = false
        btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray))
    }

    fun initializeButtonStyle(destination:Destination?, btn:Button){
        if(destination?.let { it1 -> DestinationManager.instance.isAddedToFav(it1) } == true){
            styleButton(btn)
        }
    }

    fun initializeInfo(destination:Destination?, cityField:TextView, info:TextView){
        if (destination != null) {
            cityField.text = destination.city
            info.text = destination.country + "\n" + destination.category + "\n" + destination.activity +  "\n$" + destination.price
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