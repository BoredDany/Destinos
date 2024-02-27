package com.example.destination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.destination.model.Destination
import com.example.destination.model.DestinationManager

class RecomendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendation)

        val title = findViewById<TextView>(R.id.title)
        val cityField = findViewById<TextView>(R.id.city)
        val info = findViewById<TextView>(R.id.info)

        if(DestinationManager.instance.favs.isEmpty()){
            title.text = "Recomendaciones: NA"
        }else{
            val city = DestinationManager.instance.buildRecomendations()
            val destination: Destination? = city?.let { DestinationManager.instance.getDestinationbyCity(it) }
            if (destination != null) {
                cityField.text = destination.city
                info.text = destination.country + "\n" + destination.category + "\n" + destination.activity +  "\n$" + destination.price
            }
        }

    }
}