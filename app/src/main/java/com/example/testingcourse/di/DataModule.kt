package com.example.testingcourse.di

import com.example.testingcourse.core.data.coroutines.DefaultDispatchersProvider
import com.example.testingcourse.core.domain.coroutines.DispatchersProvider
import com.example.testingcourse.productList.data.repository.ProductRepositoryImpl
import com.example.testingcourse.productList.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider {
        return defaultDispatchersProvider
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository {
        return productRepositoryImpl
    }


}