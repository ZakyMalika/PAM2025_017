package com.example.proditi.apiservice

import com.example.proditi.modeldata.*
import retrofit2.http.*

interface PeminjamanService {
    @Headers("Accept: application/json")
    @GET("peminjaman")
    suspend fun getPeminjaman(): PeminjamanResponse

    @Headers("Accept: application/json")
    @GET("peminjaman/{id}")
    suspend fun getPeminjamanById(@Path("id") id: Int): PeminjamanResponse

    @Headers("Accept: application/json")
    @POST("peminjaman")
    suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    @Headers("Accept: application/json")
    @PUT("peminjaman/{id}")
    suspend fun updatePeminjaman(@Path("id") id: Int, @Body peminjaman: Peminjaman)

    @Headers("Accept: application/json")
    @DELETE("peminjaman/{id}")
    suspend fun deletePeminjaman(@Path("id") id: Int)

    // Data Barang
    @GET("barang")
    suspend fun getBarang(): BarangResponse

    @POST("barang")
    suspend fun insertBarang(@Body barang: Barang)

    // Data Peminjam
    @GET("peminjam")
    suspend fun getPeminjam(): PeminjamResponse

    @POST("peminjam")
    suspend fun insertPeminjam(@Body peminjam: Peminjam)
}