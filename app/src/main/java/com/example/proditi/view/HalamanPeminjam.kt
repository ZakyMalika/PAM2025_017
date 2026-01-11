package com.example.proditi.uicontroller.view.peminjam

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proditi.modeldata.Peminjam
import com.example.proditi.uicontroller.route.DestinasiPeminjamHome
import com.example.proditi.viewmodel.peminjam.PeminjamHomeViewModel
import com.example.proditi.viewmodel.peminjam.PeminjamUiState
import com.example.proditi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjamHome(
    navigateBack: () -> Unit,
    navigateToEntry: () -> Unit,
    onDetailClick: (Int) -> Unit,
    viewModel: PeminjamHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) { viewModel.getPeminjam() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiPeminjamHome.titleRes) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        when (val state = viewModel.uiState) {
            is PeminjamUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            is PeminjamUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Gagal memuat data") }
            is PeminjamUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                    items(state.peminjam) { item ->
                        PeminjamCard(
                            peminjam = item,
                            onClick = { onDetailClick(item.id) },
                            onDelete = { viewModel.deletePeminjam(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PeminjamCard(peminjam: Peminjam, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { onClick() }) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(text = peminjam.namaPeminjam, style = MaterialTheme.typography.titleMedium)
                Text(text = "NIM: ${peminjam.nim}", style = MaterialTheme.typography.bodyMedium)
                Text(text = peminjam.noHp, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}