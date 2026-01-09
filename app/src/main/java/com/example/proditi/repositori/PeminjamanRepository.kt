package com.example.proditi.repositori

import com.example.proditi.modeldata.*

interface PeminjamanRepository {
    suspend fun getPeminjaman(): List<Peminjaman>
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun updatePeminjaman(id: Int, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(id: Int)
    suspend fun getPeminjamanById(id: Int): Peminjaman
    suspend fun getBarang(): List<Barang>
    suspend fun getPeminjam(): List<Peminjam>
}