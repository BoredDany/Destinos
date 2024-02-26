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

class RecomendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendation)

        val title = findViewById<TextView>(R.id.recomendationText)
        val recomendList = findViewById<ListView>(R.id.recomendationsList)

        if(DestinationManager.instance.favs.isEmpty()){
            title.text = "Recomendaciones: NA"
        }else{
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DestinationManager.instance.buildRecomendations())
            recomendList.adapter = adapter
        }

        recomendList.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(baseContext, DetailActivity::class.java)
                intent.putExtra("city", recomendList.getItemAtPosition(position).toString())
                startActivity(intent)
            }
        })
    }
}