package com.example.destination

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.destination.model.Destination
import com.example.destination.model.DestinationManager
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val city = intent.getStringExtra("city")
        var cityField = findViewById<TextView>(R.id.city)
        var info = findViewById<TextView>(R.id.info)
        val favBtn = findViewById<Button>(R.id.favsBtn)
        var destination: Destination? = city?.let { DestinationManager.instance.getDestinationbyCity(it) }

        initializeButtonStyle(destination, favBtn)
        initializeInfo(destination, cityField, info)

        favBtn.setOnClickListener {
            styleButton(favBtn)
            if(!DestinationManager.instance.isAddedToFav(destination)){
                DestinationManager.instance.addFav(destination)
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
}