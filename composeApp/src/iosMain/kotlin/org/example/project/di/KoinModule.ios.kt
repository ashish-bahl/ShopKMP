package org.example.project.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.getStorefrontDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<StorefrontDatabase> { getStorefrontDatabase() }
    single<HttpClientEngineFactory<*>> { Darwin }
}
