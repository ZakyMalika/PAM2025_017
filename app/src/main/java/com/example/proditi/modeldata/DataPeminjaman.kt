package com.example.proditi.modeldata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


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
data class KategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
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
    val id: Int = 0,
    @SerialName("nama_barang") val namaBarang: String,
    val kondisi: String,
    @SerialName("kategori_id") val kategoriId: Int,
    val kategori: Kategori? = null
)

@Serializable
data class Peminjam(
    val id: Int,

    @SerialName("nama_peminjam")
    val namaPeminjam: String,

    @SerialName("nim_nik") // Sesuai kolom database
    val nim: String,       // Kita pakai nama variabel 'nim' di Kotlin agar singkat

    @SerialName("no_hp")   // Sesuai kolom database
    val noHp: String       // Kita pakai nama variabel 'noHp' di Kotlin
)

@Serializable
data class PeminjamDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Peminjam // Single Object
)

@Serializable
data class Kategori(
    val id: Int = 0,
    @SerialName("nama_kategori") val namaKategori: String
)


@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)

@Serializable
data class BarangDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Barang
)

//@Serializable
//data class PeminjamDetailResponse(
//    val status: Boolean,
//    val message: String,
//    val data: Peminjam
//)

@Serializable
data class PeminjamanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Peminjaman
)
@Serializable
data class PeminjamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjaman>
)

@Serializable
data class AllPeminjamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjaman>
)

//@Serializable
//data class PeminjamanDetailResponse(
//    val status: Boolean,
//    val message: String,
//    val data: Peminjaman
//)