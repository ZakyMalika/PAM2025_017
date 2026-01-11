package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiBarangHome
import com.example.proditi.viewmodel.BarangEntryViewModel
import com.example.proditi.viewmodel.BarangHomeUiState
import com.example.proditi.viewmodel.BarangHomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangHome(
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Otomatis refresh data saat masuk halaman
    LaunchedEffect(Unit) {
        viewModel.getBarang()
    }

    Scaffold(
        topBar = { 
            ProdiTITopAppBar(
                title = DestinasiBarangHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Barang")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.barangUiState) {
            is BarangHomeUiState.Loading -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
            is BarangHomeUiState.Error -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error Terjadi")
            }
            is BarangHomeUiState.Success -> {
                if (state.barang.isEmpty()) {
                    Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Tidak ada data barang")
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(state.barang) { data ->
                            Card(
                                onClick = { onDetailClick(data.id) },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        text = data.namaBarang,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(text = "Kondisi: ${data.kondisi}")
                                    Text(text = "Kategori: ${data.kategori?.namaKategori ?: "-"}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBarangEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { 
            ProdiTITopAppBar(
                title = "Tambah Barang",
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
            OutlinedTextField(
                value = viewModel.uiState.namaBarang,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaBarang = it)) },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = viewModel.uiState.kondisi,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kondisi = it)) },
                label = { Text("Kondisi") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenuField(
                label = "Pilih Kategori",
                options = viewModel.listKategori.map { it.id.toString() to it.namaKategori },
                selectedId = viewModel.uiState.kategoriId,
                onSelected = { id ->
                    viewModel.updateUiState(viewModel.uiState.copy(kategoriId = id))
                }
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveBarang()
                        navigateBack()
                    }
                },
                enabled = viewModel.isInputValid(),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Simpan")
            }
        }
    }
}
