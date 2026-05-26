package com.example.testingcourse.productList.domain.model

import java.time.Instant

enum class PromotionType {
    BUY_X_PAY_Y,
    PERCENT
}

data class Promotion(
    val id: String,
    val productIds: List<String>,
    val type: PromotionType,
    val value: Double,
    val buyQuantity: Int? = null,
    val startTime: Instant,
    val endTime: Instant
)