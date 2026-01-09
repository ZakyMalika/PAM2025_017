package com.example.proditi.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proditi.ProditiApplications
import com.example.proditi.viewmodel.*

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiPeminjaman().container.peminjamanRepository) }
        initializer { EntryViewModel(aplikasiPeminjaman().container.peminjamanRepository) }

        // Tambahan untuk Detail & Edit
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiPeminjaman().container.peminjamanRepository
            )
        }
    }
}

fun CreationExtras.aplikasiPeminjaman(): ProditiApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProditiApplications)