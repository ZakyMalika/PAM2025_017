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
    @GET("barang/{id}")
    suspend fun getBarangById(@Path("id") id: Int): BarangResponse
    @POST("barang")
    suspend fun insertBarang(@Body barang: Barang)
    @PUT("barang/{id}")
    suspend fun updateBarang(@Path("id") id: Int, @Body barang: Barang)
    @DELETE("barang/{id}")
    suspend fun deleteBarang(@Path("id") id: Int)

    // Data Peminjam
    @GET("peminjam")
    suspend fun getPeminjam(): PeminjamResponse
    @GET("peminjam/{id}")
    suspend fun getPeminjamById(@Path("id") id: Int): PeminjamResponse
    @POST("peminjam")
    suspend fun insertPeminjam(@Body peminjam: Peminjam)
    @PUT("peminjam/{id}")
    suspend fun updatePeminjam(@Path("id") id: Int, @Body peminjam: Peminjam)
    @DELETE("peminjam/{id}")
    suspend fun deletePeminjam(@Path("id") id: Int)

    // Data Kategori
    @GET("kategori")
    suspend fun getKategori(): KategoriResponse
    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") id: Int): KategoriResponse
    @POST("kategori")
    suspend fun insertKategori(@Body kategori: Kategori)
    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") id: Int, @Body kategori: Kategori)
    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") id: Int)
}