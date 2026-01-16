package com.example.proditi.uicontroller.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category // Icon Kategori
import androidx.compose.material.icons.filled.ChevronRight // Icon Panah
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Kategori
import com.example.proditi.uicontroller.route.DestinasiKategoriHome
import com.example.proditi.viewmodel.KategoriHomeUiState
import com.example.proditi.viewmodel.KategoriHomeViewModel
import com.example.proditi.viewmodel.provider.PenyediaViewModel
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import com.example.proditi.viewmodel.KategoriEntryViewModel
import kotlinx.coroutines.delay
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
    LaunchedEffect(Unit) {
        viewModel.getKategori()
    }

    Scaffold(
        topBar = {
            ProdiTITopAppBar(
                title = DestinasiKategoriHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Kategori")
            }
        },
    ) { innerPadding ->
        when (val state = viewModel.kategoriUiState) {
            // STATE LOADING
            is KategoriHomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            // STATE ERROR
            is KategoriHomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Gagal memuat data", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
            // STATE SUKSES
            is KategoriHomeUiState.Success -> {
                if (state.kategori.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Belum ada kategori",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.kategori) { data ->
                            KategoriCard(
                                kategori = data,
                                onClick = { onDetailClick(data.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Komponen Kartu Kategori yang Cantik
@Composable
fun KategoriCard(
    kategori: Kategori,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Kategori (Kiri)
            Icon(
                imageVector = Icons.Default.Category,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Teks (Tengah)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ID: ${kategori.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Icon Panah (Kanan)
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Detail",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
    val snackbarHostState = remember { SnackbarHostState() }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
            // Field Input dengan Icon
            OutlinedTextField(
                value = viewModel.uiState.namaKategori,
                onValueChange = {
                    viewModel.updateUiState(viewModel.uiState.copy(namaKategori = it))
                    isError = false
                },
                label = { Text("Nama Kategori") },
                placeholder = { Text("Masukkan nama kategori") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Category, contentDescription = null)
                },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Simpan
            Button(
                onClick = {
                    if (viewModel.uiState.namaKategori.isBlank()) {
                        isError = true
                        errorMessage = "Nama Kategori tidak boleh kosong!"
                    } else {
                        coroutineScope.launch {
                            viewModel.saveKategori()
                            snackbarHostState.showSnackbar("Data berhasil ditambahkan")
                            // Delay sedikit agar snackbar sempat muncul sekejap sebelum pindah
                            delay(200)
                            navigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Simpan")
            }
        }
    }
}