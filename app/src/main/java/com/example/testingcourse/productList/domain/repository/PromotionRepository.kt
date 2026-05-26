package com.example.testingcourse.productList.domain.repository

import com.example.testingcourse.productList.domain.model.Promotion
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    fun getActivePromotions(): Flow<List<Promotion>>
    suspend fun refreshPromotions()
}