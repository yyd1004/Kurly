package com.example.kurly.service

import com.example.kurly.response.ProductsData
import com.example.kurly.response.SectionsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("sections")
    suspend fun getSections(
        @Query("page") page: Int
    ): Response<SectionsData>

    @GET("section/products")
    suspend fun getProducts(
        @Query("sectionId") sectionId: Int,
        @Query("returnPosition") returnPosition: Int
    ): Response<ProductsData>
}