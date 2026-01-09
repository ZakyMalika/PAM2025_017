package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
            is BarangHomeUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is BarangHomeUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is BarangHomeUiState.Success -> {
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
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.uiState.namaBarang,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaBarang = it)) },
                label = { Text("Nama Barang") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.kondisi,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kondisi = it)) },
                label = { Text("Kondisi") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.kategoriId,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(kategoriId = it)) },
                label = { Text("Kategori ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveBarang()
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
