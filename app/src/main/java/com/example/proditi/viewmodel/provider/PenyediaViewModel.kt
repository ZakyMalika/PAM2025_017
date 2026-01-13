package com.example.proditi.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proditi.ProditiApplications
import com.example.proditi.viewmodel.*
import com.example.proditi.viewmodel.barang.*
import com.example.proditi.viewmodel.barang.BarangDetailViewModel
//import com.example.proditi.viewmodel.kategori.*
import com.example.proditi.viewmodel.peminjam.* // Pastikan import ini ada
import com.example.proditi.viewmodel.peminjam.PeminjamDetailViewModel
import com.example.proditi.viewmodel.auth.LoginViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        // --- MODUL TRANSAKSI (PEMINJAMAN) ---
        initializer { HomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { EntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { DetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { EditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }

        // --- MODUL BARANG ---
        initializer { BarangHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { BarangEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { BarangDetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { BarangEditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }

        // --- MODUL PEMINJAM (USER/MAHASISWA) - INI YANG TADI ERROR ---
        initializer {
            PeminjamHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository)
        }
        initializer {
            PeminjamEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository)
        }
        // Pastikan bagian ini ada!
        initializer {
            PeminjamDetailViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }
        initializer {
            PeminjamEditViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }

        // --- MODUL KATEGORI ---
        initializer { KategoriHomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriEntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriDetailViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { KategoriEditViewModel(createSavedStateHandle(), aplikasiPeminjaman().container.peminjamanRepository) }

//        user
        initializer {
            LoginViewModel(aplikasiPeminjaman().container.peminjamanRepository)
        }
    }
}

fun CreationExtras.aplikasiPeminjaman(): ProditiApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProditiApplications)