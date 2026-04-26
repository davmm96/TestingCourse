package com.example.testingcourse.di

import android.content.Context
import androidx.room.Room
import com.example.testingcourse.core.data.coroutines.DefaultDispatchersProvider
import com.example.testingcourse.core.domain.coroutines.DispatchersProvider
import com.example.testingcourse.productList.data.local.database.MiniMarketDatabase
import com.example.testingcourse.productList.data.local.database.dao.ProductDao
import com.example.testingcourse.productList.data.local.database.dao.PromotionDao
import com.example.testingcourse.productList.data.repository.ProductRepositoryImpl
import com.example.testingcourse.productList.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider =
        defaultDispatchersProvider

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository =
        productRepositoryImpl

    @Provides
    fun providesProductDao(database: MiniMarketDatabase): ProductDao =
        database.productDao()


    @Provides
    fun providesPromotionDao(database: MiniMarketDatabase): PromotionDao =
        database.promotionDao()


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MiniMarketDatabase =
        Room.databaseBuilder(
            context = context,
            klass = MiniMarketDatabase::class.java,
            name = MiniMarketDatabase.DB_NAME
        ).build()
}