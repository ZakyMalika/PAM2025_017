package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamEntry
import com.example.proditi.viewmodel.peminjam.PeminjamEntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamEntry(
    navigateBack: () -> Unit,
    viewModel: PeminjamEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiPeminjamEntry.titleRes) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // INPUT NAMA (Bebas Text)
            OutlinedTextField(
                value = viewModel.uiState.namaPeminjam,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(namaPeminjam = it))
                    isError = false
                },
                label = { Text("Nama Peminjam") },
                isError = isError && viewModel.uiState.namaPeminjam.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            // INPUT NIM/NIK (HANYA ANGKA)
            OutlinedTextField(
                value = viewModel.uiState.nim,
                onValueChange = {
                    // Filter: Hanya terima jika inputnya angka
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.updateUiState(viewModel.uiState.copy(nim = it))
                        isError = false
                    }
                },
                label = { Text("NIM / NIK") },
                isError = isError && viewModel.uiState.nim.isBlank(),
                // Keyboard khusus angka
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // INPUT NO HP (HANYA ANGKA)
            OutlinedTextField(
                value = viewModel.uiState.noHp,
                onValueChange = {
                    // Filter: Hanya terima jika inputnya angka
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.updateUiState(viewModel.uiState.copy(noHp = it))
                        isError = false
                    }
                },
                label = { Text("No HP") },
                isError = isError && viewModel.uiState.noHp.isBlank(),
                supportingText = { if (isError) Text(errorMessage, color = MaterialTheme.colorScheme.error) },
                // Keyboard khusus telepon
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (viewModel.uiState.namaPeminjam.isBlank() || viewModel.uiState.nim.isBlank() || viewModel.uiState.noHp.isBlank()) {
                        isError = true
                        errorMessage = "Semua kolom harus diisi!"
                    } else {
                        coroutineScope.launch {
                            viewModel.savePeminjam(onSuccess = navigateBack)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Simpan") }
        }
    }
}