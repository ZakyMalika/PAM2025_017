package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Kategori
import com.example.proditi.uicontroller.route.DestinasiBarangEdit
import com.example.proditi.viewmodel.barang.BarangEditViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangEdit(
    navigateBack: () -> Unit,
    viewModel: BarangEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isError by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(DestinasiBarangEdit.titleRes) },
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
                leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null) },
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
                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
                isError = isError && viewModel.uiState.kondisi.isBlank(),
                supportingText = { if (isError && viewModel.uiState.kondisi.isBlank()) Text("Kondisi wajib diisi", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth()
            )

            // KATEGORI
            DropdownKategoriEdit(
                kategoriList = viewModel.kategoriList,
                selectedKategoriId = viewModel.uiState.kategoriId,
                onKategoriSelected = { selectedId ->
                    viewModel.updateUiState(viewModel.uiState.copy(kategoriId = selectedId))
                    isError = false
                }
            )
            if (isError && viewModel.uiState.kategoriId == null) {
                Text("Kategori wajib dipilih", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // TOMBOL UPDATE
            Button(
                onClick = {
                    if (viewModel.uiState.namaBarang.isBlank() ||
                        viewModel.uiState.kondisi.isBlank() ||
                        viewModel.uiState.kategoriId == null) {
                        isError = true
                    } else {
                        viewModel.updateBarang(onSuccess = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Data Barang Berhasil Diupdate")
                                delay(600)
                                navigateBack()
                            }
                        })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Update Barang")
            }
        }
    }
}

// Reuse fungsi dropdown tapi tambah icon
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownKategoriEdit(
    kategoriList: List<Kategori>,
    selectedKategoriId: Int?,
    onKategoriSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedKategoriName = kategoriList.find { it.id == selectedKategoriId }?.namaKategori ?: "Pilih Kategori"

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedKategoriName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Kategori") },
            leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            kategoriList.forEach { kategori ->
                DropdownMenuItem(
                    text = { Text(text = kategori.namaKategori) },
                    onClick = { onKategoriSelected(kategori.id); expanded = false }
                )
            }
        }
    }
}