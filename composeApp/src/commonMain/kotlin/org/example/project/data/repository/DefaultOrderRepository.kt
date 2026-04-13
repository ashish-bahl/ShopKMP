package org.example.project.data.repository

import kotlinx.coroutines.flow.map
import org.example.project.data.local.dao.StorefrontDao
import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.data.local.mapper.toDomainModel
import org.example.project.data.local.mapper.toLocalEntity
import org.example.project.domain.model.Order
import org.example.project.domain.repository.OrderRepository

class DefaultOrderRepository(
    private val storefrontDatabase: StorefrontDatabase,
) : OrderRepository {
    private  val dao: StorefrontDao = storefrontDatabase.storefrontDao()

    override suspend fun placeOrder(order: Order) = dao.placeOrder(order.toLocalEntity())

    override fun observeOrders() = dao.observeOrders()
        .map { orders -> orders.map { it.toDomainModel() } }

    override suspend fun getOrder(id: String) = dao.getOrder(id)?.toDomainModel()
}
