package com.example.proditi.uicontroller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proditi.uicontroller.route.DestinasiBarangHome
import com.example.proditi.uicontroller.route.DestinasiDashboard
import com.example.proditi.uicontroller.route.DestinasiHome
import com.example.proditi.uicontroller.route.DestinasiKategoriHome
import com.example.proditi.uicontroller.route.DestinasiPeminjamHome

@Composable
fun HalamanDashboard(
    onMenuClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        // Kita tidak menggunakan TopAppBar standar agar bisa membuat Header custom yang lebih besar
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 1. BAGIAN HEADER (Banner)
            DashboardHeader()

            // 2. BAGIAN MENU GRID
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Menu Utama",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(menuItems) { item ->
                        MenuCard(
                            title = item.title,
                            icon = item.icon,
                            backgroundColor = item.color,
                            onClick = { onMenuClick(item.route) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp) // Header lebih tinggi
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)) // Lengkungan di bawah
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
    ) {
        // Hiasan Background (Lingkaran transparan)
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = (-40).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterStart)
        ) {
            // Icon Profil Kecil / Avatar
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selamat Datang,",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
            Text(
                text = "Admin Prodi TI",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Kelola inventaris dengan mudah",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp), // Tinggi kartu fixed agar seragam
        shape = RoundedCornerShape(24.dp), // Sudut lebih membulat
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Hiasan warna di pojok kanan atas
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp)
                    .clip(CircleShape)
                    .background(backgroundColor.copy(alpha = 0.2f))
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon dalam lingkaran
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(backgroundColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = backgroundColor,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Judul Menu
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// Data Class untuk Menu Item dengan Warna
data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

// Daftar Menu dengan Warna Spesifik
val menuItems = listOf(
    MenuItem(
        "Peminjaman",
        Icons.Default.Assignment,
        DestinasiHome.route,
        Color(0xFF4CAF50) // Hijau
    ),
    MenuItem(
        "Barang",
        Icons.Default.Inventory,
        DestinasiBarangHome.route,
        Color(0xFF2196F3) // Biru
    ),
    MenuItem(
        "Peminjam",
        Icons.Default.People,
        DestinasiPeminjamHome.route,
        Color(0xFFFF9800) // Oranye
    ),
    MenuItem(
        "Kategori",
        Icons.Default.Category,
        DestinasiKategoriHome.route,
        Color(0xFF9C27B0) // Ungu
    )
)