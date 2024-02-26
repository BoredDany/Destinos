package com.example.destination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.destination.model.DestinationManager

class FavsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs)

        val title = findViewById<TextView>(R.id.favsText)
        val favsList = findViewById<ListView>(R.id.favsDest)
        val destinationsList = findViewById<ListView>(R.id.favsDest)

        initializeList(title, favsList)

        destinationsList.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(baseContext, DetailActivity::class.java)
                intent.putExtra("city", destinationsList.getItemAtPosition(position).toString())
                startActivity(intent)
            }
        })

    }

    fun initializeList(title:TextView, favsList:ListView){
        if(DestinationManager.instance.favs.isEmpty()){
            title.text = "Destinos favritos: NA"
        }else{
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DestinationManager.instance.buildFav())
            favsList.adapter = adapter
        }
    }
}