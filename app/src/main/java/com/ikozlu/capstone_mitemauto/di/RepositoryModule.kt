package com.ikozlu.capstone_mitemauto.di

import com.ikozlu.capstone_mitemauto.data.repository.ProductRepository
import com.ikozlu.capstone_mitemauto.data.source.local.ProductDao
import com.ikozlu.capstone_mitemauto.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesProductRepository(
        productService: ProductService,
        productDao: ProductDao
    ): ProductRepository =
        ProductRepository(productService, productDao)


}