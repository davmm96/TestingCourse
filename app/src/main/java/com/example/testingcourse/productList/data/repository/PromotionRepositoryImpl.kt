package com.example.testingcourse.productList.data.repository

import com.example.testingcourse.core.domain.coroutines.DispatchersProvider
import com.example.testingcourse.productList.data.local.LocalDataSource
import com.example.testingcourse.productList.data.mappers.toDomain
import com.example.testingcourse.productList.data.mappers.toEntity
import com.example.testingcourse.productList.data.remote.RemoteDataSource
import com.example.testingcourse.productList.domain.model.Promotion
import com.example.testingcourse.productList.domain.repository.PromotionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PromotionRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatchers: DispatchersProvider,
    private val json: Json
) : PromotionRepository {

    private val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    private val refreshMutex = Mutex()

    override fun getActivePromotions(): Flow<List<Promotion>> {
        return localDataSource.getAllPromotions()
            .map { entities ->
                entities.mapNotNull {
                    it.toDomain(json)
                }
            }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch

                    try {
                        refreshPromotions()
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

    override suspend fun refreshPromotions() {
        withContext(dispatchers.io) {
            val promotions = remoteDataSource.getPromotions().getOrThrow()
            val promotionsEntity = promotions.mapNotNull {
                it.toEntity(json)
            }
            localDataSource.savePromotions(promotionsEntity)
        }
    }
}