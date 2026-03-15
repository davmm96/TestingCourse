package com.example.testingcourse.productList.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.testingcourse.productList.data.local.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromotionDao {

    @Query("SELECT * FROM promotions")
    fun getAllPromotions(): Flow<List<PromotionEntity>>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getProductById(id: String): Flow<PromotionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotions(products: List<PromotionEntity>)

    @Query("DELETE FROM promotions")
    suspend fun clearPromotions()

    @Transaction
    suspend fun replaceAll(promotions: List<PromotionEntity>) {
        clearPromotions()
        insertPromotions(promotions)
    }
}
