package br.com.digitalhouse.aula1301_firebaserealtime

import android.media.MediaRouter

data class Product(var name: String, var quantity: Int, var price: Double, var category: Category)

data class Category(var id: Int, var name: String)
