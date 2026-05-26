package com.example.testingcourse.productList.domain.usecase

import com.example.testingcourse.productList.domain.model.ProductWithPromotion
import com.example.testingcourse.productList.domain.repository.ProductRepository
import com.example.testingcourse.productList.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val getPromotionForProductUseCase: GetPromotionForProductUseCase
) {
    operator fun invoke(): Flow<List<ProductWithPromotion>> {
        return combine(
            productRepository.getProducts(),
            promotionRepository.getActivePromotions()
        ) { products, promotions ->

            val now = Instant.now()

            val activePromotions = promotions.filter {
                it.startTime <= now && it.endTime >= now
            }

            products.map { product ->
                val promotion = getPromotionForProductUseCase(product = product, activePromotions)
                ProductWithPromotion(product = product, promotion = promotion)
            }
        }
    }
}