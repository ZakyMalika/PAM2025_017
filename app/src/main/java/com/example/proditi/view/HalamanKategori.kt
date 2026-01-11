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
import com.example.proditi.uicontroller.route.DestinasiKategoriHome
import com.example.proditi.viewmodel.KategoriEntryViewModel
import com.example.proditi.viewmodel.KategoriHomeUiState
import com.example.proditi.viewmodel.KategoriHomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanKategoriHome(
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiKategoriHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Kategori")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.kategoriUiState) {
            is KategoriHomeUiState.Loading -> Text("Loading...", Modifier.padding(innerPadding))
            is KategoriHomeUiState.Error -> Text("Error Terjadi", Modifier.padding(innerPadding))
            is KategoriHomeUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(state.kategori) { data ->
                        Card(
                            onClick = { onDetailClick(data.id) },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    text = data.namaKategori,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "ID: ${data.id}")
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
fun HalamanKategoriEntry(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = "Tambah Kategori",
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
                value = viewModel.uiState.namaKategori,
                onValueChange = { viewModel.updateUiState(viewModel.uiState.copy(namaKategori = it)) },
                label = { Text("Nama Kategori") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveKategori()
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
