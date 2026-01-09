package com.example.proditi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proditi.modeldata.Peminjaman
import com.example.proditi.repositori.PeminjamanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val peminjaman: List<Peminjaman>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repository: PeminjamanRepository) : ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPeminjaman()
    }

    fun getPeminjaman() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                HomeUiState.Success(repository.getPeminjaman())
            } catch (e: IOException) {
                // Tambahkan ini untuk melihat error koneksi (sinyal/IP salah)
                e.printStackTrace()
                HomeUiState.Error
            } catch (e: HttpException) {
                // Tambahkan ini untuk melihat error server (404/500)
                e.printStackTrace()
                HomeUiState.Error
            } catch (e: Exception) {
                // Tambahkan ini untuk melihat error Serialisasi/Parsing JSON
                android.util.Log.e("HomeViewModel", "Error tak terduga: ${e.message}")
                e.printStackTrace()
                HomeUiState.Error
            }
        }
    }
}