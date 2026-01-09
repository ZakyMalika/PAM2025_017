package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiEntry
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

    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
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
            // Dropdown Barang
            DropdownMenuField(
                label = "Pilih Barang",
                options = viewModel.listBarang.map { it.id.toString() to it.namaBarang },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.barangId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.barangId.toString(),
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(barangId = id.toInt()))
                }
            )

            // Dropdown Peminjam
            DropdownMenuField(
                label = "Pilih Peminjam",
                options = viewModel.listPeminjam.map { it.id.toString() to it.namaPeminjam },
                selectedId = if (viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId == 0) "" else viewModel.uiStatePeminjaman.detailPeminjaman.peminjamId.toString(),
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(peminjamId = id.toInt()))
                }
            )

            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalPinjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalPinjam = it)) },
                label = { Text("Tanggal Pinjam (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.uiStatePeminjaman.detailPeminjaman.tanggalKembali,
                onValueChange = { viewModel.updateUiState(viewModel.uiStatePeminjaman.detailPeminjaman.copy(tanggalKembali = it)) },
                label = { Text("Tanggal Kembali (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.savePeminjaman()
                        navigateBack()
                    }
                },
                enabled = viewModel.uiStatePeminjaman.isEntryValid,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Simpan Peminjaman")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<Pair<String, String>>, // Pair(ID, DisplayName)
    selectedId: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = options.find { it.first == selectedId }?.second ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.second) },
                    onClick = {
                        onSelected(option.first)
                        expanded = false
                    }
                )
            }
        }
    }
}
