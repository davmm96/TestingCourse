package com.example.testingcourse.productList.domain.usecase

import com.example.testingcourse.core.domain.extension.roundTo2Decimals
import com.example.testingcourse.productList.domain.model.Product
import com.example.testingcourse.productList.domain.model.ProductPromotion
import com.example.testingcourse.productList.domain.model.Promotion
import com.example.testingcourse.productList.domain.model.PromotionType
import javax.inject.Inject

class GetPromotionForProductUseCase @Inject constructor() {

    operator fun invoke(product: Product, promotions: List<Promotion>): ProductPromotion? {

        val productPromos = promotions.filter { it.productIds.contains(product.id) }

        val percentPromo = productPromos.filter { promotion ->
            promotion.type == PromotionType.PERCENT
        }.maxByOrNull { it.value }

        if (percentPromo != null) {
            val percent = percentPromo.value.coerceIn(0.0, 100.0)
            val discountedPrice = (product.price * (1 - percent / 100.0)).roundTo2Decimals()

            return ProductPromotion.Percent(percent = percent, discountedPrice = discountedPrice)
        }

        val buyPayPromo = productPromos.firstOrNull { promotion ->
            promotion.type == PromotionType.BUY_X_PAY_Y
        }

        if (buyPayPromo != null) {
            val buy = buyPayPromo.buyQuantity ?: return null
            val pay = buyPayPromo.value.toInt().coerceIn(0, buy)

            return ProductPromotion.BuyYPayY(
                buy = buy,
                pay = pay,
                label = "$buy X $pay"
            )
        }

        return null
    }
}