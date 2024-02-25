package com.example.destination

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buildAdapter(categoryChosen))
        destinationsList.adapter = adapter


    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val pais: InputStream = assets.open("destinos.json")
            val size = pais.available()
            val buffer = ByteArray(size)
            pais.read(buffer)
            pais.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    fun buildAdapter(category: String?): MutableList<String> {
        val json = JSONObject(loadJSONFromAsset())
        val arrayCountries = json.getJSONArray("destinos")
        var countriesArr = mutableListOf<String>()
        for(i in 0 until arrayCountries.length()){
            val jsonObject = arrayCountries.getJSONObject(i)
            val capital = jsonObject.getString("nombre")
            val countryCategory = jsonObject.getString("categoria")

            if(category == "Todos" || category == countryCategory){
                countriesArr.add(capital)
            }

        }
        return countriesArr
    }
}