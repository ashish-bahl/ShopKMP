package org.example.project.domain.model

@kotlinx.serialization.Serializable
data class Order(
    val id: String,
    val cartItems: List<Product>,
    val totalAmount: Double,
    val totalQuantity: Int,
    val name: String,
    val address: String,
    val number: String,
)
