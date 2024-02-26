package com.example.destination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class HomeActivity : AppCompatActivity(),  AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnExplore = findViewById<Button>(R.id.destinosBtn)
        val btnRecomend = findViewById<Button>(R.id.recomendBtn)
        val btnFavs = findViewById<Button>(R.id.favsBtn)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = this

        btnExplore.setOnClickListener {
            setExploreIntent(spinner)
        }

        btnFavs.setOnClickListener {
            val intent = Intent(this, FavsActivity::class.java)
            startActivity(intent)
        }

        btnRecomend.setOnClickListener {
            val intent = Intent(this, RecomendationActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val filter = parent?.selectedItem
        Toast.makeText(baseContext, "Filter: "+ filter, Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun setExploreIntent(spinner:Spinner){
        val intent = Intent(this, ExploreActivity::class.java)
        val bundle = Bundle()
        bundle.putString("filter", spinner.selectedItem.toString())
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }
}