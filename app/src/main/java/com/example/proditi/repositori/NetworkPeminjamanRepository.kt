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
    override suspend fun getBarangById(id: Int): Barang = peminjamanApiService.getBarangById(id).data.first()
    override suspend fun insertBarang(barang: Barang) = peminjamanApiService.insertBarang(barang)
    override suspend fun updateBarang(id: Int, barang: Barang) = peminjamanApiService.updateBarang(id, barang)
    override suspend fun deleteBarang(id: Int) = peminjamanApiService.deleteBarang(id)
    
    override suspend fun getPeminjam(): List<Peminjam> = peminjamanApiService.getPeminjam().data
    override suspend fun getPeminjamById(id: Int): Peminjam = peminjamanApiService.getPeminjamById(id).data.first()
    override suspend fun insertPeminjam(peminjam: Peminjam) = peminjamanApiService.insertPeminjam(peminjam)
    override suspend fun updatePeminjam(id: Int, peminjam: Peminjam) = peminjamanApiService.updatePeminjam(id, peminjam)
    override suspend fun deletePeminjam(id: Int) = peminjamanApiService.deletePeminjam(id)

    override suspend fun getKategori(): List<Kategori> = peminjamanApiService.getKategori().data
    override suspend fun getKategoriById(id: Int): Kategori = peminjamanApiService.getKategoriById(id).data.first()
    override suspend fun insertKategori(kategori: Kategori) = peminjamanApiService.insertKategori(kategori)
    override suspend fun updateKategori(id: Int, kategori: Kategori) = peminjamanApiService.updateKategori(id, kategori)
    override suspend fun deleteKategori(id: Int) = peminjamanApiService.deleteKategori(id)
}