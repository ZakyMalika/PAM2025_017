package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.viewmodel.EditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEdit(
    navigateBack: () -> Unit,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Peminjaman") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        ) {

            DropdownMenuField(
                label = "Pilih Barang",
                options = viewModel.listBarang.map { it.id.toString() to it.namaBarang },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.barangId.toString(),
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
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(peminjamId = id.toInt()))
                }
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = it)) },
                label = { Text("Tanggal Pinjam") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))



            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = it)) },
                label = { Text("Tanggal Kembali") },
                modifier = Modifier.fillMaxWidth()
            )

            // Button Update
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updatePeminjaman()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Update Data")
            }
        }
    }
}