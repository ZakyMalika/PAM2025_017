package com.example.proditi.uicontroller.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Kategori
import com.example.proditi.uicontroller.route.DestinasiBarangEntry
import com.example.proditi.viewmodel.barang.BarangEntryViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangEntry(
    navigateBack: () -> Unit,
    viewModel: BarangEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiBarangEntry.titleRes) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
            // Input Nama Barang
            OutlinedTextField(
                value = viewModel.uiState.namaBarang,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaBarang = it)) },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input Kondisi
            OutlinedTextField(
                value = viewModel.uiState.kondisi,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kondisi = it)) },
                label = { Text("Kondisi (Baik/Rusak)") },
                modifier = Modifier.fillMaxWidth()
            )

            // DROPDOWN KATEGORI
            DropdownKategoriEntry(
                kategoriList = viewModel.kategoriList,
                selectedKategoriId = viewModel.uiState.kategoriId,
                onKategoriSelected = { selectedId ->
                    viewModel.updateUiState(viewModel.uiState.copy(kategoriId = selectedId))
                }
            )

            // Tombol Simpan
            Button(
                onClick = { viewModel.saveBarang(onSuccess = navigateBack) },
                modifier = Modifier.fillMaxWidth(),
                // Tombol aktif jika semua field terisi
                enabled = viewModel.uiState.namaBarang.isNotEmpty() &&
                        viewModel.uiState.kondisi.isNotEmpty() &&
                        viewModel.uiState.kategoriId != null
            ) {
                Text("Simpan")
            }
        }
    }
}

// Fungsi Dropdown Khusus Entry
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