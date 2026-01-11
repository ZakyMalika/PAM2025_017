package com.example.proditi.viewmodel.peminjam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.repositori.PeminjamanRepository
import com.example.proditi.uicontroller.route.DestinasiPeminjamDetail
import com.example.proditi.uicontroller.route.DestinasiPeminjamEdit
import kotlinx.coroutines.launch

// --- 1. HOME VIEWMODEL ---
sealed interface PeminjamUiState {
    data class Success(val peminjam: List<Peminjam>) : PeminjamUiState
    object Error : PeminjamUiState
    object Loading : PeminjamUiState
}

class PeminjamHomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState: PeminjamUiState by mutableStateOf(PeminjamUiState.Loading)
        private set

    init { getPeminjam() }

    fun getPeminjam() {
        viewModelScope.launch {
            uiState = PeminjamUiState.Loading
            uiState = try {
                PeminjamUiState.Success(repository.getPeminjam())
            } catch (e: Exception) {
                e.printStackTrace()
                PeminjamUiState.Error
            }
        }
    }

    fun deletePeminjam(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePeminjam(id)
                getPeminjam() // Refresh data setelah hapus
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = PeminjamUiState.Error
            }
        }
    }
}

// --- 2. STATE FORM (Digunakan oleh Entry & Edit) ---
// Perbaikan: Menggunakan 'nim' dan 'noHp'
data class PeminjamEntryUiState(
    val namaPeminjam: String = "",
    val nim: String = "",    // Dulu alamat
    val noHp: String = ""    // Dulu noTelp
)

// --- 3. ENTRY VIEWMODEL ---
class PeminjamEntryViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var uiState by mutableStateOf(PeminjamEntryUiState())
        private set

    fun updateUiState(newState: PeminjamEntryUiState) {
        uiState = newState
    }

    fun savePeminjam(onSuccess: () -> Unit) {
        if (uiState.namaPeminjam.isNotBlank()) {
            viewModelScope.launch {
                try {
                    // Perbaikan: Masukkan data sesuai field baru
                    repository.insertPeminjam(
                        Peminjam(
                            id = 0,
                            namaPeminjam = uiState.namaPeminjam,
                            nim = uiState.nim,
                            noHp = uiState.noHp
                        )
                    )
                    onSuccess()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

// --- 4. DETAIL VIEWMODEL ---
sealed interface PeminjamDetailUiState {
    data class Success(val peminjam: Peminjam) : PeminjamDetailUiState
    object Error : PeminjamDetailUiState
    object Loading : PeminjamDetailUiState
}

class PeminjamDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PeminjamanRepository
) : ViewModel() {
    private val id: Int = checkNotNull(savedStateHandle[DestinasiPeminjamDetail.peminjamId])
    var uiState: PeminjamDetailUiState by mutableStateOf(PeminjamDetailUiState.Loading)
        private set

    init { getPeminjamById() }

    fun getPeminjamById() {
        viewModelScope.launch {
            uiState = PeminjamDetailUiState.Loading
            uiState = try {
                val data = repository.getPeminjamById(id)
                PeminjamDetailUiState.Success(data)
            } catch (e: Exception) {
                e.printStackTrace()
                PeminjamDetailUiState.Error
            }
        }
    }

    fun deletePeminjam(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deletePeminjam(id)
                onSuccess()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}

// --- 5. EDIT VIEWMODEL ---
//class PeminjamEditViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val repository: PeminjamanRepository
//) : ViewModel() {
//    private val id: Int = checkNotNull(savedStateHandle[DestinasiPeminjamEdit.peminjamId])
//
//    var uiState by mutableStateOf(PeminjamEntryUiState())
//        private set
//
//    init { loadPeminjam() }
//
//    private fun loadPeminjam() {
//        viewModelScope.launch {
//            try {
//                val data = repository.getPeminjamById(id)
//                // Perbaikan: Mapping data dari server (nim/noHp) ke UI State
//                uiState = PeminjamEntryUiState(
//                    namaPeminjam = data.namaPeminjam,
//                    nim = data.nim,   // Ambil dari variabel nim di data class
//                    noHp = data.noHp  // Ambil dari variabel noHp di data class
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun updateUiState(newState: PeminjamEntryUiState) { uiState = newState }
//
//    fun updatePeminjam(onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            try {
//                // Perbaikan: Kirim update dengan field baru
//                repository.updatePeminjam(
//                    id,
//                    Peminjam(
//                        id = id,
//                        namaPeminjam = uiState.namaPeminjam,
//                        nim = uiState.nim,
//                        noHp = uiState.noHp
//                    )
//                )
//                onSuccess()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}