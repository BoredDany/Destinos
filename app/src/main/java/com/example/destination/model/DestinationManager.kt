package com.example.destination.model


import android.app.Activity
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class  DestinationManager private constructor() {
    lateinit var destinations: JSONArray
    var destinationsList: MutableList<Destination> = mutableListOf()
    var favs: MutableList<Destination> = mutableListOf()
    val jsonFile = "destinos.json"

    companion object{
        val instance: DestinationManager by lazy { DestinationManager()}
    }
    fun loadJSONFromAsset(activity: Activity): String? {
        try {
            val assets = activity.assets
            val inputStream = assets.open(jsonFile)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    fun getDestinations(activity: Activity): JSONArray {
        val json = JSONObject(loadJSONFromAsset(activity))
        return json.getJSONArray("destinos")
    }
    fun buildAdapter(categoryE: String?, activity: Activity): MutableList<String> {
        try {
            val jsonArray = getDestinations(activity)
            val countriesArr = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val capital = jsonObject.getString("nombre")
                val category = jsonObject.getString("categoria")
                val country = jsonObject.getString("pais")
                val activity = jsonObject.getString("plan")
                val price = jsonObject.getString("precio")
                var destination = Destination(capital, country, category, activity, price)
                if(!isAdded(capital)){
                    destinationsList.add(destination)
                }
                if (categoryE.equals("Todos", ignoreCase = true) || category.equals(categoryE, ignoreCase = true)) {
                    countriesArr.add(capital)
                }
            }
            return countriesArr
        } catch (ex: Exception) {
            ex.printStackTrace()
            return mutableListOf()
        }
    }

    fun getDestinationbyCity(city:String): Destination {
        lateinit var  destination: Destination
        for(dest: Destination in destinationsList){
            if(dest.city.equals(city)){
                destination = dest
            }
        }
        return destination
    }

    fun isAddedToFav(destination: Destination?): Boolean{
        if(destination != null){
            for(dest: Destination in favs){
                if(dest.city.equals(destination.city)){
                    return true
                }
            }
        }
        return false
    }

    fun isAdded(destination: String): Boolean{
        for(dest: Destination in destinationsList){
            if(dest.city.equals(destination)){
                return true
            }
        }
        return false
    }
    fun addFav(destination: Destination?){
        if(destination != null){
            favs.add(destination)
        }
    }

    fun buildFav(): MutableList<String>{
        val favsDestinations = mutableListOf<String>()
        for(dest: Destination in favs){
            favsDestinations.add(dest.city)
        }
        return favsDestinations
    }

    fun buildRecomendations(): String {
        val recomendations: MutableList<String> = mutableListOf()
        val favsCategories = mutableMapOf<String, Int>()
        var maxFrequency = 0

        for (dest: Destination in favs) {
            val category = dest.category
            favsCategories[category] = favsCategories.getOrDefault(category, 0) + 1
            maxFrequency = maxOf(maxFrequency, favsCategories[category]!!)
        }

        for ((category, frequency) in favsCategories) {
            if (frequency == maxFrequency) {
                for (dest in destinationsList) {
                    if (dest.category == category) {
                        recomendations.add(dest.city)
                    }
                }
            }
        }
        if(recomendations.size == 0){
            return ""
        }
        return recomendations.random()
    }
}