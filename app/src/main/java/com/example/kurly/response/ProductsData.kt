package com.example.kurly.response

import com.google.gson.annotations.SerializedName

class ProductsData {
    @SerializedName("data")
    val data: ArrayList<Products>? = null

    var returnPosition : Int? = null

    data class Products(
        val id: Int = 0,
        val name: String = "",
        val image: String = "",
        val originalPrice: Float? = null,
        val discountedPrice: Float? = null,
        val isSoldOut: Boolean = false,
    )
}