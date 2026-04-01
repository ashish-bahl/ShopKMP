package org.example.project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class OrderEntity(
    @PrimaryKey val id: String,
    val cartItems: List<CartItemEntity>,
    val totalAmount: Double,
    val totalQuantity: Int,
    val name: String,
    val address: String,
    val number: String,
)
