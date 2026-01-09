package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.viewmodel.EntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEntry(
    navigateBack: () -> Unit,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tambah Peminjaman") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Form Sederhana (Dropdown perlu implementasi komponen ExposedDropdownMenu)
            // Ini contoh Text Field Manual dulu agar tidak error compile
            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = it)) },
                label = { Text("Tanggal Pinjam (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = it)) },
                label = { Text("Tanggal Kembali (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tambahkan Dropdown Logic di sini nanti

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.savePeminjaman()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Simpan")
            }
        }
    }
}