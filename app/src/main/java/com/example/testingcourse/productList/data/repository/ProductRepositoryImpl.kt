package com.example.testingcourse.productList.data.repository

import com.example.testingcourse.core.domain.coroutines.DispatchersProvider
import com.example.testingcourse.productList.data.remote.RemoteDataSource
import com.example.testingcourse.productList.domain.model.Product
import com.example.testingcourse.productList.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val dispatchers: DispatchersProvider
) : ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: String): Flow<Product?> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProduct() {
        withContext(dispatchers.io) {
            remoteDataSource.getProducts()
        }
    }
}