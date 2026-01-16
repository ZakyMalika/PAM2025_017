package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamEntry
import com.example.proditi.viewmodel.peminjam.PeminjamEntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamEntry(
    navigateBack: () -> Unit,
    viewModel: PeminjamEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
            // INPUT NAMA
            OutlinedTextField(
                value = viewModel.uiState.namaPeminjam,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(namaPeminjam = it))
                    isError = false
                },
                label = { Text("Nama Peminjam") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = isError && viewModel.uiState.namaPeminjam.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            // INPUT NIM/NIK
            OutlinedTextField(
                value = viewModel.uiState.nim,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.updateUiState(viewModel.uiState.copy(nim = it))
                        isError = false
                    }
                },
                label = { Text("NIM / NIK") },
                leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null) }, // Icon Kartu Identitas
                isError = isError && viewModel.uiState.nim.isBlank(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // INPUT NO HP
            OutlinedTextField(
                value = viewModel.uiState.noHp,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.updateUiState(viewModel.uiState.copy(noHp = it))
                        isError = false
                    }
                },
                label = { Text("No HP") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                isError = isError && viewModel.uiState.noHp.isBlank(),
                supportingText = { if (isError) Text(errorMessage, color = MaterialTheme.colorScheme.error) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (viewModel.uiState.namaPeminjam.isBlank() || viewModel.uiState.nim.isBlank() || viewModel.uiState.noHp.isBlank()) {
                        isError = true
                        errorMessage = "Semua kolom harus diisi!"
                    } else {
                        viewModel.savePeminjam(onSuccess = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Peminjam Berhasil Disimpan")
                                delay(600)
                                navigateBack()
                            }
                        })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Simpan")
            }
        }
    }
}