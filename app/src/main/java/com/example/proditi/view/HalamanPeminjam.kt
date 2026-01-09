package com.example.proditi.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.uicontroller.route.DestinasiPeminjamHome
import com.example.proditi.viewmodel.PeminjamEntryViewModel
import com.example.proditi.viewmodel.PeminjamHomeUiState
import com.example.proditi.viewmodel.PeminjamHomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamHome(
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeminjamHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = { 
            ProdiTITopAppBar(
                title = DestinasiPeminjamHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Peminjam")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.peminjamUiState) {
            is PeminjamHomeUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is PeminjamHomeUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is PeminjamHomeUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(state.peminjam) { data ->
                        Card(
                            onClick = { onDetailClick(data.id) },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = data.namaPeminjam,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "NIM/NIK: ${data.nimNik}")
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
fun HalamanPeminjamEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeminjamEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { 
            ProdiTITopAppBar(
                title = "Tambah Peminjam",
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
                value = viewModel.uiState.namaPeminjam,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaPeminjam = it)) },
                label = { Text("Nama Peminjam") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.nimNik,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(nimNik = it)) },
                label = { Text("NIM/NIK") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.uiState.noHp,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(noHp = it)) },
                label = { Text("No HP") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.savePeminjam()
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
