package com.example.productcatalog

import android.app.Application
import com.example.productcatalog.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProductCatalogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProductCatalogApplication)
            modules(appModule)
        }
    }
}
