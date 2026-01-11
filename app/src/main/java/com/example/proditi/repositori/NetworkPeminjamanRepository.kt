package com.example.proditi.repositori

import com.example.proditi.apiservice.PeminjamanService
import com.example.proditi.modeldata.*
import java.io.IOException

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
) : PeminjamanRepository {

    // --- BAGIAN PEMINJAMAN ---
    override suspend fun getPeminjaman(): List<Peminjaman> {
        return peminjamanApiService.getPeminjaman().data
    }

    override suspend fun getPeminjamanById(id: Int): Peminjaman {
        // PERBAIKAN: Ambil .data dari PeminjamanDetailResponse
        return peminjamanApiService.getPeminjamanById(id).data
    }

    override suspend fun insertPeminjaman(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun updatePeminjaman(id: Int, peminjaman: Peminjaman) {
        peminjamanApiService.updatePeminjaman(id, peminjaman)
    }

    override suspend fun deletePeminjaman(id: Int) {
        try {
            val response = peminjamanApiService.deletePeminjaman(id)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus data peminjaman. Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }


    // --- BAGIAN BARANG ---
    override suspend fun getBarang(): List<Barang> {
        return peminjamanApiService.getBarang().data
    }

    override suspend fun getBarangById(id: Int): Barang {
        // PERBAIKAN: Ambil .data dari BarangDetailResponse
        return peminjamanApiService.getBarangById(id).data
    }

    override suspend fun insertBarang(barang: Barang) {
        peminjamanApiService.insertBarang(barang)
    }

    override suspend fun updateBarang(id: Int, barang: Barang) {
        peminjamanApiService.updateBarang(id, barang)
    }

    override suspend fun deleteBarang(id: Int) {
        try {
            val response = peminjamanApiService.deleteBarang(id)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus barang. Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }


    // --- BAGIAN PEMINJAM ---
    override suspend fun getPeminjam(): List<Peminjam> {
        return peminjamanApiService.getPeminjam().data
    }

    override suspend fun getPeminjamById(id: Int): Peminjam {
        // PERBAIKAN: Ambil .data dari PeminjamDetailResponse
        return peminjamanApiService.getPeminjamById(id).data
    }

    override suspend fun insertPeminjam(peminjam: Peminjam) {
        peminjamanApiService.insertPeminjam(peminjam)
    }

    override suspend fun updatePeminjam(id: Int, peminjam: Peminjam) {
        peminjamanApiService.updatePeminjam(id, peminjam)
    }

    override suspend fun deletePeminjam(id: Int) {
        try {
            val response = peminjamanApiService.deletePeminjam(id)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus peminjam. Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }


    // --- BAGIAN KATEGORI ---
    override suspend fun getKategori(): List<Kategori> {
        return peminjamanApiService.getKategori().data
    }

    override suspend fun getKategoriById(id: Int): Kategori {
        // PERBAIKAN UTAMA: Mengambil objek 'data' dari KategoriDetailResponse
        return peminjamanApiService.getKategoriById(id).data
    }

    override suspend fun insertKategori(kategori: Kategori) {
        peminjamanApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(id: Int, kategori: Kategori) {
        peminjamanApiService.updateKategori(id, kategori)
    }

    override suspend fun deleteKategori(id: Int) {
        try {
            val response = peminjamanApiService.deleteKategori(id)
            if (!response.isSuccessful) {
                throw IOException("Gagal menghapus kategori. Code: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}