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
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider {
        return defaultDispatchersProvider
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository {
        return productRepositoryImpl
    }

    @Provides
    fun providesProductDao(database: MiniMarketDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun providesPromotionDao(database: MiniMarketDatabase): PromotionDao {
        return database.promotionDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MiniMarketDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MiniMarketDatabase::class.java,
            name = "minimarket_database"
        ).build()
    }


}