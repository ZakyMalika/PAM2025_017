package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.viewmodel.EditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEdit(
    navigateBack: () -> Unit,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { TopAppBar(title = { Text("Edit Peminjaman") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        ) {

            // Dropdown Barang
            DropdownMenuField(
                label = "Pilih Barang",
                options = viewModel.listBarang.map { it.id.toString() to it.namaBarang },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.barangId.toString(),
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0,
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(barangId = id.toInt()))
                }
            )
            Spacer(Modifier.height(8.dp))

            // Dropdown Peminjam
            DropdownMenuField(
                label = "Pilih Peminjam",
                options = viewModel.listPeminjam.map { it.id.toString() to it.namaPeminjam },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId.toString(),
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0,
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(peminjamId = id.toInt()))
                }
            )
            Spacer(Modifier.height(8.dp))

            // Date Picker Pinjam
            DatePickerField(
                label = "Tanggal Pinjam",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onDateSelected = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = it)) },
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam.isBlank()
            )

            Spacer(Modifier.height(8.dp))

            // Date Picker Kembali
            DatePickerField(
                label = "Tanggal Kembali",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onDateSelected = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = it)) },
                isError = isError && viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali.isBlank()
            )

            if (isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            // Button Update
            Button(
                onClick = {
                    if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0 ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0 ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam.isBlank() ||
                        viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali.isBlank()
                    ) {
                        isError = true
                        errorMessage = "Semua data harus diisi!"
                    } else {
                        coroutineScope.launch {
                            viewModel.updatePeminjaman()
                            snackbarHostState.showSnackbar("Data Berhasil Diupdate")
                            delay(600)
                            navigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Update Data")
            }
        }
    }
}