package com.example.kurly.response

import com.google.gson.annotations.SerializedName

class SectionsData {
    @SerializedName("data")
    val data: ArrayList<Sections>? = null

    @SerializedName("paging")
    val paging: Pageing? = null

    data class Sections(
        val id: Int = 0,
        val title: String = "",
        val type: String = "",
        val url: String = "",
        var products: ArrayList<ProductsData.Products>? = null
    )

    data class Pageing(
        val next_page: Int = 0
    )
}