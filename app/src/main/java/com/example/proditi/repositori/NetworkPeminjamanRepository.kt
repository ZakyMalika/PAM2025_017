package com.example.proditi.repositori

import com.example.proditi.apiservice.PeminjamanService
import com.example.proditi.modeldata.*

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
) : PeminjamanRepository {
    override suspend fun getPeminjaman(): List<Peminjaman> = peminjamanApiService.getPeminjaman().data
    override suspend fun insertPeminjaman(peminjaman: Peminjaman) = peminjamanApiService.insertPeminjaman(peminjaman)
    override suspend fun updatePeminjaman(id: Int, peminjaman: Peminjaman) = peminjamanApiService.updatePeminjaman(id, peminjaman)
    override suspend fun deletePeminjaman(id: Int) = peminjamanApiService.deletePeminjaman(id)
    override suspend fun getPeminjamanById(id: Int): Peminjaman = peminjamanApiService.getPeminjamanById(id).data.first()
    override suspend fun getBarang(): List<Barang> = peminjamanApiService.getBarang().data
    override suspend fun getPeminjam(): List<Peminjam> = peminjamanApiService.getPeminjam().data
}