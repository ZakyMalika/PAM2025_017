package com.example.proditi.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.*
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiStatePeminjaman by mutableStateOf(PeminjamanUiState())
        private set

    var listBarang by mutableStateOf<List<Barang>>(emptyList())
    var listPeminjam by mutableStateOf<List<Peminjam>>(emptyList())

    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            try {
                // 1. Ambil data
                val allBarang = repository.getBarang()
                val allPeminjam = repository.getPeminjam()
                val activeTransactions = repository.getPeminjaman()

                // 2. Siapkan Formatter Tanggal & Hari Ini
                // Format harus sesuai dengan yang dari database (yyyy-MM-dd)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val today = LocalDate.now()

                // 3. LOGIKA FILTERING LANJUTAN
                listBarang = allBarang.filter { barang ->
                    // Cek apakah barang ini ada di transaksi yang MASIH AKTIF
                    // Transaksi dianggap aktif jika: ID Barang Sama DAN Tanggal Kembali BELUM LEWAT
                    val isSedangDipinjam = activeTransactions.any { transaksi ->
                        if (transaksi.barangId == barang.id) {
                            try {
                                val tanggalKembali = LocalDate.parse(transaksi.tanggalKembali, formatter)

                                // Jika tanggal kembali masih di masa depan atau hari ini,
                                // berarti barang masih dipinjam (Unavailable).
                                // Jika tanggal kembali < hari ini, berarti sudah lewat (Available).
                                val masihDipinjam = tanggalKembali.isAfter(today) || tanggalKembali.isEqual(today)
                                masihDipinjam
                            } catch (e: Exception) {
                                // Jika gagal parsing tanggal, anggap tidak dipinjam (aman) atau sesuaikan kebutuhan
                                false
                            }
                        } else {
                            false
                        }
                    }

                    // Ambil barang HANYA JIKA TIDAK sedang dipinjam
                    !isSedangDipinjam
                }

                listPeminjam = allPeminjam
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiState(detailPeminjaman: DetailPeminjaman) {
        uiStatePeminjaman = PeminjamanUiState(
            detailPeminjaman = detailPeminjaman,
            isEntryValid = validateInput(detailPeminjaman)
        )
    }

    private fun validateInput(uiState: DetailPeminjaman = uiStatePeminjaman.detailPeminjaman): Boolean {
        return with(uiState) {
            tanggalPinjam.isNotBlank() && tanggalKembali.isNotBlank() && barangId != 0 && peminjamId != 0
        }
    }

    suspend fun savePeminjaman() {
        if (validateInput()) {
            repository.insertPeminjaman(uiStatePeminjaman.detailPeminjaman.toPeminjaman())
        }
    }
}

// ... (Bagian Data Class di bawah tetap sama) ...
data class PeminjamanUiState(
    val detailPeminjaman: DetailPeminjaman = DetailPeminjaman(),
    val isEntryValid: Boolean = false
)

data class DetailPeminjaman(
    val id: Int = 0,
    val tanggalPinjam: String = "",
    val tanggalKembali: String = "",
    val barangId: Int = 0,
    val peminjamId: Int = 0
)

fun DetailPeminjaman.toPeminjaman(): Peminjaman = Peminjaman(
    id = id,
    tanggalPinjam = tanggalPinjam,
    tanggalKembali = tanggalKembali,
    barangId = barangId,
    peminjamId = peminjamId
)