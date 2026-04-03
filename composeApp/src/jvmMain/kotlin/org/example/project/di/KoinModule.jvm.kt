package org.example.project.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.getStorefrontDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<StorefrontDatabase> { getStorefrontDatabase() }
    single<HttpClientEngineFactory<*>> { OkHttp }
}
