package com.example.kurly.di

import com.example.kurly.repository.ProductRepository
import com.example.kurly.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideProductRepository(
        @NetworkModule.product productService: ProductService
    ) : ProductRepository {
        return ProductRepository(productService)
    }
}