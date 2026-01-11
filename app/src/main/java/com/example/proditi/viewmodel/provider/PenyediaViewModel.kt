package com.example.proditi.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proditi.ProditiApplications
import com.example.proditi.viewmodel.*
// Gunakan import wildcard (*) agar semua ViewModel di dalam folder barang/kategori terbaca
import com.example.proditi.viewmodel.barang.*
import com.example.proditi.viewmodel.barang.BarangDetailViewModel
// Jika Peminjam ada di folder khusus, import juga. Jika di root viewmodel, import viewmodel.* sudah cukup.

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // --- Modul Peminjaman (Transaksi) ---
        initializer { HomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { EntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { DetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { EditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }

        // --- Modul Barang ---
        initializer {
            BarangHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository)
        }
        initializer {
            BarangEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository)
        }
        // PERBAIKAN: Hapus tanda "->" dan pastikan Import BarangDetailViewModel sudah ada
        initializer {
            BarangDetailViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }
        initializer {
            BarangEditViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }

        // --- Modul Peminjam (User/Mahasiswa) ---
        // Pastikan nama class ViewModel ini sesuai dengan file yang Anda buat
        initializer { PeminjamHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { PeminjamEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { PeminjamDetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { PeminjamEditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }

        // --- Modul Kategori ---
        // Pastikan nama class ViewModel ini sesuai dengan file yang Anda buat (misal: HalamanHomeKategoriViewModel atau KategoriHomeViewModel)
        // Di sini saya sesuaikan dengan kode yang Anda kirim barusan:
        initializer { KategoriHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriDetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriEditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
    }
}

fun CreationExtras.aplikasiPeminjaman(): ProditiApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProditiApplications)