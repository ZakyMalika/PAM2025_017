package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
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
        topBar = {
            TopAppBar(
                title = { Text("Edit Peminjaman") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DropdownMenuField(
                label = "Pilih Barang",
                options = viewModel.listBarang.map { it.id.toString() to it.namaBarang },
                selectedId = viewModel.uiStatePeminjaman.detailPeminjaman.barangId.toString(),
                leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                onSelected = { id -> viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(barangId = id.toInt())) }
            )

            DropdownMenuField(
                label = "Pilih Peminjam",
                options = viewModel.listPeminjam.map { it.id.toString() to it.namaPeminjam },
                selectedId = viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId.toString(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                onSelected = { id -> viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(peminjamId = id.toInt())) }
            )

            DatePickerField(
                label = "Tanggal Pinjam",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onDateSelected = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = it)) }
            )

            DatePickerField(
                label = "Tanggal Kembali",
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onDateSelected = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = it)) }
            )

            if (isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0 || viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0) {
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Update Data")
            }
        }
    }
}