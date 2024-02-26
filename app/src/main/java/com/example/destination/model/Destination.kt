package com.example.destination.model

class Destination {
    var city: String
    var country: String
    var category: String
    var activity: String
    var price: String

    constructor(city: String, country: String, category: String, activity: String, price: String) {
        this.city = city
        this.country = country
        this.category = category
        this.activity = activity
        this.price = price
    }
}