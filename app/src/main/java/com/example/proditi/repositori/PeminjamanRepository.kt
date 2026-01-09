package com.example.proditi.repositori

import com.example.proditi.modeldata.*

interface PeminjamanRepository {
    // Peminjaman
    suspend fun getPeminjaman(): List<Peminjaman>
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun updatePeminjaman(id: Int, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(id: Int)
    suspend fun getPeminjamanById(id: Int): Peminjaman

    // Barang
    suspend fun getBarang(): List<Barang>
    suspend fun getBarangById(id: Int): Barang
    suspend fun insertBarang(barang: Barang)
    suspend fun updateBarang(id: Int, barang: Barang)
    suspend fun deleteBarang(id: Int)

    // Peminjam
    suspend fun getPeminjam(): List<Peminjam>
    suspend fun getPeminjamById(id: Int): Peminjam
    suspend fun insertPeminjam(peminjam: Peminjam)
    suspend fun updatePeminjam(id: Int, peminjam: Peminjam)
    suspend fun deletePeminjam(id: Int)

    // Kategori
    suspend fun getKategori(): List<Kategori>
    suspend fun getKategoriById(id: Int): Kategori
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(id: Int, kategori: Kategori)
    suspend fun deleteKategori(id: Int)
}