package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamEntry
import com.example.proditi.viewmodel.peminjam.PeminjamEntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamEntry(
    navigateBack: () -> Unit,
    viewModel: PeminjamEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(DestinasiPeminjamEntry.titleRes) }) }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = viewModel.uiState.namaPeminjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaPeminjam = it)) },
                label = { Text("Nama Peminjam") },
                modifier = Modifier.fillMaxWidth()
            )
            // GANTI ALAMAT -> NIM/NIK
            OutlinedTextField(
                value = viewModel.uiState.nim,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(nim = it)) },
                label = { Text("NIM / NIK") },
                modifier = Modifier.fillMaxWidth()
            )
            // GANTI NO TELP -> NO HP
            OutlinedTextField(
                value = viewModel.uiState.noHp,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(noHp = it)) },
                label = { Text("No HP") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { viewModel.savePeminjam(onSuccess = navigateBack) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Simpan") }
        }
    }
}