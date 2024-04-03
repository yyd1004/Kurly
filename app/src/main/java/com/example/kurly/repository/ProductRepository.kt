package com.example.kurly.repository

import com.example.kurly.response.ProductsData
import com.example.kurly.response.SectionsData
import com.example.kurly.service.ProductService
import retrofit2.Response
import javax.inject.Inject

class ProductRepository
@Inject
constructor(
    private val service: ProductService
) {
    suspend fun getSections(
        page: Int
    ): Response<SectionsData> {
        return service.getSections(page)
    }

    suspend fun getProducts(
        sectionId: Int,
        returnPosition : Int
    ): Response<ProductsData> {
        return service.getProducts(sectionId, returnPosition)
    }
}