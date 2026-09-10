package com.example.productcatalog.di

import com.example.productcatalog.data.api.ProductApi
import com.example.productcatalog.data.repository.ProductRepositoryImpl
import com.example.productcatalog.domain.repository.ProductRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ProductApi::class.java)
    }

    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    viewModel {
        com.example.productcatalog.presentation.list.ProductListViewModel(get())
    }

    viewModel { params ->
        com.example.productcatalog.presentation.detail.ProductDetailViewModel(get(), params.get())
    }
}
