package com.example.testingcourse.productList.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testingcourse.productList.data.local.database.dao.ProductDao
import com.example.testingcourse.productList.data.local.database.dao.PromotionDao
import com.example.testingcourse.productList.data.local.database.entity.ProductEntity
import com.example.testingcourse.productList.data.local.database.entity.PromotionEntity

@Database(
    entities = [
        ProductEntity::class,
        PromotionEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class MiniMarketDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun promotionDao(): PromotionDao

}