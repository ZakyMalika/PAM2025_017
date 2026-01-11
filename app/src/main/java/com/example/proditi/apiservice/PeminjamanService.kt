package com.example.proditi.apiservice

import com.example.proditi.modeldata.*
import retrofit2.Response // Tambahkan import ini
import retrofit2.http.*

interface PeminjamanService {
    @Headers("Accept: application/json")
    @GET("peminjaman")
    suspend fun getPeminjaman(): PeminjamanResponse

    // PERBAIKAN 1: Gunakan PeminjamanDetailResponse
    @Headers("Accept: application/json")
    @GET("peminjaman/{id}")
    suspend fun getPeminjamanById(@Path("id") id: Int): PeminjamanDetailResponse

    @Headers("Accept: application/json")
    @POST("peminjaman")
    suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    @Headers("Accept: application/json")
    @PUT("peminjaman/{id}")
    suspend fun updatePeminjaman(@Path("id") id: Int, @Body peminjaman: Peminjaman)

    @Headers("Accept: application/json")
    @DELETE("peminjaman/{id}")
    suspend fun deletePeminjaman(@Path("id") id: Int): Response<Unit> // Tambahkan Response<Unit> agar bisa dicek statusnya

    // Data Barang
    @GET("barang")
    suspend fun getBarang(): BarangResponse

    // PERBAIKAN 2: Gunakan BarangDetailResponse
    @GET("barang/{id}")
    suspend fun getBarangById(@Path("id") id: Int): BarangDetailResponse

    @POST("barang")
    suspend fun insertBarang(@Body barang: Barang)

    @PUT("barang/{id}")
    suspend fun updateBarang(@Path("id") id: Int, @Body barang: Barang)

    @DELETE("barang/{id}")
    suspend fun deleteBarang(@Path("id") id: Int): Response<Unit>

    // Data Peminjam
    @GET("peminjam")
    suspend fun getPeminjam(): PeminjamResponse

    // PERBAIKAN 3: Gunakan PeminjamDetailResponse
    @GET("peminjam/{id}")
    suspend fun getPeminjamById(@Path("id") id: Int): PeminjamDetailResponse

    @POST("peminjam")
    suspend fun insertPeminjam(@Body peminjam: Peminjam)

    @PUT("peminjam/{id}")
    suspend fun updatePeminjam(@Path("id") id: Int, @Body peminjam: Peminjam)

    @DELETE("peminjam/{id}")
    suspend fun deletePeminjam(@Path("id") id: Int): Response<Unit>

    // Data Kategori
    @GET("kategori")
    suspend fun getKategori(): KategoriResponse

    // PERBAIKAN 4: Gunakan KategoriDetailResponse
    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") id: Int): KategoriDetailResponse

    @POST("kategori")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") id: Int, @Body kategori: Kategori)

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") id: Int): Response<Unit>
}