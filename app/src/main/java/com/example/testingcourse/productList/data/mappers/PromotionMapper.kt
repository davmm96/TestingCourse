package com.example.testingcourse.productList.data.mappers

import com.example.testingcourse.productList.data.local.database.entity.PromotionEntity
import com.example.testingcourse.productList.data.remote.response.PromotionResponse
import com.example.testingcourse.productList.domain.model.Promotion
import com.example.testingcourse.productList.domain.model.PromotionType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.Instant

fun PromotionResponse.toEntity(json: Json): PromotionEntity? {

    if (startAtEpoch == null || endAtEpoch == null) return null

    val productIds = listOf(productId)
    val productIdsJson = json.encodeToString(
        serializer = ListSerializer(String.serializer()),
        value = productIds
    )

    return PromotionEntity(
        id = id,
        productIds = productIdsJson,
        type = type,
        percent = percent,
        buyX = buyX,
        payY = payY,
        startAtEpoch = startAtEpoch,
        endAtEpoch = endAtEpoch
    )
}


fun PromotionEntity.toDomain(json: Json): Promotion? {

    val decodedProductIdList = runCatching {
        json.decodeFromString(
            deserializer = ListSerializer(String.serializer()),
            string = productIds
        )
    }.getOrNull()

    decodedProductIdList ?: return null

    val finalType = runCatching {
        PromotionType.valueOf(type.trim().uppercase())
    }.getOrNull()

    finalType ?: return null


    val finalOfferValue = when (finalType) {
        PromotionType.BUY_X_PAY_Y -> percent
        PromotionType.PERCENT -> payY
    }?.toDouble()

    finalOfferValue ?: return null

    return Promotion(
        id = id,
        productIds = decodedProductIdList,
        type = finalType,
        value = finalOfferValue,
        buyQuantity = buyX,
        startTime = Instant.ofEpochSecond(startAtEpoch),
        endTime = Instant.ofEpochSecond(endAtEpoch)
    )
}