package com.example.mysqlfirst.viewmodel

import android.util.Log // <-- Tambahkan import ini
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysqlfirst.modeldata.DataSiswa
import com.example.mysqlfirst.repositori.RepositoryDataSiswa
import com.example.mysqlfirst.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUIDetail
    object Error: StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(savedStateHandle: SavedStateHandle, private val repositoryDataSiswa: RepositoryDataSiswa):
    ViewModel() {
    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])
    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa(){
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                // Cetak log jika berhasil untuk memastikan data diterima
                Log.d("DetailViewModel", "SUCCESS: Data siswa dimuat: $siswa")
                StatusUIDetail.Success(satusiswa = siswa)
            }
            // PERBAIKAN 1: Tambahkan logging untuk masalah jaringan
            catch (e: IOException){
                Log.e("DetailViewModel", "ERROR (IOException): Masalah koneksi jaringan.", e)
                StatusUIDetail.Error
            }
            // PERBAIKAN 2: Tambahkan logging untuk error dari server (HTTP)
            catch (e: HttpException){
                Log.e("DetailViewModel", "ERROR (HttpException): Gagal memuat data. Kode: ${e.code()}", e)
                StatusUIDetail.Error
            }
            // PERBAIKAN 3: Menangkap error tak terduga lainnya
            catch (e: Exception) {
                Log.e("DetailViewModel", "ERROR (Exception): Terjadi kesalahan tak terduga.", e)
                StatusUIDetail.Error
            }
        }
    }

    suspend fun hapusSatuSiswa() {
        try {
            val resp: Response<Void> = repositoryDataSiswa.hapusSatuSiswa(idSiswa)

            if (resp.isSuccessful){
                Log.d("DetailViewModel", "Sukses Hapus Data : ${resp.message()}")
            } else {
                Log.e("DetailViewModel", "Gagal Hapus Data. Kode: ${resp.code()}, Pesan: ${resp.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("DetailViewModel", "Exception saat menghapus siswa.", e)
        }
    }
}
