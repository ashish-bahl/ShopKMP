package org.example.project.data.remote

import org.example.project.ShopKMPConfig

object ApiConfig {
    val BASE_URL = "https://${ShopKMPConfig.MOCK_API_SECRET}.mockapi.io/shopkmp/v1"
    const val ALL_PRODUCTS_PATH = "/products"
}
