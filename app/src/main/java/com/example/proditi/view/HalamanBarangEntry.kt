package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Kategori
import com.example.proditi.uicontroller.route.DestinasiBarangEntry
import com.example.proditi.viewmodel.barang.BarangEntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangEntry(
    navigateBack: () -> Unit,
    viewModel: BarangEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // State Validasi
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(DestinasiBarangEntry.titleRes) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // NAMA BARANG
            OutlinedTextField(
                value = viewModel.uiState.namaBarang,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(namaBarang = it))
                    isError = false
                },
                label = { Text("Nama Barang") },
                isError = isError && viewModel.uiState.namaBarang.isBlank(),
                supportingText = { if (isError && viewModel.uiState.namaBarang.isBlank()) Text("Nama barang wajib diisi", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth()
            )

            // KONDISI
            OutlinedTextField(
                value = viewModel.uiState.kondisi,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(kondisi = it))
                    isError = false
                },
                label = { Text("Kondisi (Baik/Rusak)") },
                isError = isError && viewModel.uiState.kondisi.isBlank(),
                supportingText = { if (isError && viewModel.uiState.kondisi.isBlank()) Text("Kondisi wajib diisi", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth()
            )

            // KATEGORI (DROPDOWN)
            // Kita tambahkan validasi manual di sini (border merah belum didukung native dropdown, jadi pakai text error di bawahnya)
            Column {
                DropdownKategoriEntry(
                    kategoriList = viewModel.kategoriList,
                    selectedKategoriId = viewModel.uiState.kategoriId,
                    onKategoriSelected = { selectedId ->
                        viewModel.updateUiState(viewModel.uiState.copy(kategoriId = selectedId))
                        isError = false
                    }
                )
                if (isError && viewModel.uiState.kategoriId == null) {
                    Text(
                        text = "Kategori wajib dipilih",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            // TOMBOL SIMPAN
            Button(
                onClick = {
                    if (viewModel.uiState.namaBarang.isBlank() ||
                        viewModel.uiState.kondisi.isBlank() ||
                        viewModel.uiState.kategoriId == null) {

                        isError = true
                        errorMessage = "Semua data wajib diisi!"
                    } else {
                        viewModel.saveBarang(onSuccess = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Data Barang Berhasil Disimpan")
                                delay(100)
                                navigateBack()
                            }
                        })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownKategoriEntry(
    kategoriList: List<Kategori>,
    selectedKategoriId: Int?,
    onKategoriSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedKategoriName = kategoriList.find { it.id == selectedKategoriId }?.namaKategori ?: "Pilih Kategori"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedKategoriName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Kategori") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            kategoriList.forEach { kategori ->
                DropdownMenuItem(
                    text = { Text(text = kategori.namaKategori) },
                    onClick = {
                        onKategoriSelected(kategori.id)
                        expanded = false
                    }
                )
            }
        }
    }
}