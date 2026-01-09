package com.example.proditi.repositori

import com.example.proditi.apiservice.PeminjamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val peminjamanRepository: PeminjamanRepository
}

class DefaultAppContainer : AppContainer {

    // Pastikan IP ini benar sesuai testing tadi
    private val baseUrl = "http://10.0.2.2:8000/api/"

    // PERBAIKAN: Konfigurasi Json agar mengabaikan field yang tidak dikenal (created_at, dll)
    private val json = Json {
        ignoreUnknownKeys = true // <--- KUNCI PERBAIKANNYA DI SINI
        isLenient = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        // Gunakan variabel 'json' yang sudah kita konfigurasi di atas
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PeminjamanService by lazy {
        retrofit.create(PeminjamanService::class.java)
    }

    override val peminjamanRepository: PeminjamanRepository by lazy {
        NetworkPeminjamanRepository(retrofitService)
    }
}