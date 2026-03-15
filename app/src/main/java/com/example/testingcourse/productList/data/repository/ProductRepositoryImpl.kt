package com.example.testingcourse.productList.data.repository

import com.example.testingcourse.core.domain.coroutines.DispatchersProvider
import com.example.testingcourse.productList.data.local.LocalDataSource
import com.example.testingcourse.productList.data.mappers.toDomain
import com.example.testingcourse.productList.data.mappers.toEntity
import com.example.testingcourse.productList.data.remote.RemoteDataSource
import com.example.testingcourse.productList.domain.model.Product
import com.example.testingcourse.productList.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatchers: DispatchersProvider
) : ProductRepository {

    private val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    private val refreshMutex = Mutex()

    override fun getProducts(): Flow<List<Product>> {
        return localDataSource.getAllProducts()
            .map { entities ->
                entities.mapNotNull {
                    it.toDomain()
                }
            }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch

                    try {
                        refreshProduct()
                    } catch (e: Exception) {

                    } finally {
                        refreshMutex.unlock()
                    }
                }
            }
            .catch {
                //TODO Add log
            }
    }

    override fun getProductById(id: String): Flow<Product?> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProduct() {
        withContext(dispatchers.io) {
            val products = remoteDataSource.getProducts().getOrThrow()
            val productsEntity = products.map {
                it.toEntity()
            }
            localDataSource.saveProducts(productsEntity)
        }
    }
}