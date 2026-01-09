package com.example.proditi.modeldata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeminjamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjaman>
)

@Serializable
data class BarangResponse(
    val status: Boolean,
    val message: String,
    val data: List<Barang>
)

@Serializable
data class PeminjamResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjam>
)

@Serializable
data class Peminjaman(
    val id: Int = 0,
    @SerialName("tanggal_pinjam") val tanggalPinjam: String,
    @SerialName("tanggal_kembali") val tanggalKembali: String,
    @SerialName("barang_id") val barangId: Int,
    @SerialName("peminjam_id") val peminjamId: Int,
    // Relasi (Optional karena tidak dikirim saat POST)
    val barang: Barang? = null,
    val peminjam: Peminjam? = null
)

@Serializable
data class Barang(
    val id: Int,
    @SerialName("nama_barang") val namaBarang: String,
    val kondisi: String,
    @SerialName("kategori_id") val kategoriId: Int // <--- Ubah jadi 'i' (kategori_id)
)

@Serializable
data class Peminjam(
    val id: Int,
    @SerialName("nama_peminjam") val namaPeminjam: String,
    @SerialName("nim_nik") val nimNik: String,
    @SerialName("no_hp") val noHp: String
)