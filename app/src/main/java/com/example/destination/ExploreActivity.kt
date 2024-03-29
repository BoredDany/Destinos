package com.example.destination

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.destination.model.DestinationManager
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class ExploreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val bundle = intent.getBundleExtra("bundle")
        val filter = findViewById<TextView>(R.id.filterApplied)
        val categoryChosen = bundle?.getString("filter")
        filter.text = "Destinos filtrados por: " + categoryChosen

        val destinationsList = findViewById<ListView>(R.id.destinationList)
        DestinationManager.instance.destinations = DestinationManager.instance.getDestinations(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DestinationManager.instance.buildAdapter(categoryChosen, this))
        destinationsList.adapter = adapter

        destinationsList.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(baseContext, DetailActivity::class.java)
                intent.putExtra("city", destinationsList.getItemAtPosition(position).toString())
                startActivity(intent)
            }
        })

    }

}