package com.example.testingcourse.productList.data.mappers

import com.example.testingcourse.productList.data.local.database.entity.ProductEntity
import com.example.testingcourse.productList.data.remote.response.ProductResponse
import com.example.testingcourse.productList.domain.model.Product

fun ProductResponse.toEntity(): ProductEntity {

    val finalPrice = priceCents?.div(100.0) ?: 0.0

    return ProductEntity(
        id = id,
        name = name,
        description = description,
        price = finalPrice,
        category = category,
        stock = stock,
        imageURL = imageURL
    )
}

fun ProductEntity.toDomain(): Product? {

    if (category.isNullOrEmpty()) return null

    return Product(
        id = id,
        name = name,
        description = description.orEmpty(),
        price = price,
        category = category,
        stock = stock ?: 0,
        imageURL = imageURL
    )
}