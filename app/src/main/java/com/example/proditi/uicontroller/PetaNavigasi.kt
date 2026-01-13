package com.example.proditi.uicontroller.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proditi.uicontroller.view.*
import com.example.proditi.uicontroller.view.barang.*
import com.example.proditi.uicontroller.view.peminjam.*
import com.example.proditi.view.auth.HalamanLogin

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route, // Mulai dari Login
        modifier = modifier
    ) {
        // --- 0. HALAMAN LOGIN ---
        composable(DestinasiLogin.route) {
            HalamanLogin(
                onLoginSuccess = {
                    // Berpindah ke Dashboard dan menghapus halaman Login dari history
                    navController.navigate(DestinasiDashboard.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // --- 1. DASHBOARD ---
        composable(DestinasiDashboard.route) {
            HalamanDashboard(
                onMenuClick = { route ->
                    navController.navigate(route)
                }
            )
        }

        // --- SECTION PEMINJAMAN ---
        composable(DestinasiHome.route) {
            HalamanHome(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DestinasiDetail.route}/$itemId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiEntry.route) {
            HalamanEntry(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.peminjamanId) { type = NavType.IntType })
        ) {
            HalamanDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { itemId ->
                    navController.navigate("${DestinasiEdit.route}/$itemId")
                }
            )
        }
        composable(
            route = DestinasiEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEdit.peminjamanId) { type = NavType.IntType })
        ) {
            HalamanEdit(
                navigateBack = { navController.popBackStack() }
            )
        }

        // --- SECTION BARANG ---
        composable(DestinasiBarangHome.route) {
            HalamanBarangHome(
                navigateBack = { navController.popBackStack() },
                navigateToEntry = { navController.navigate(DestinasiBarangEntry.route) },
                onDetailClick = { id -> navController.navigate("${DestinasiBarangDetail.route}/$id") }
            )
        }
        composable(DestinasiBarangEntry.route) {
            HalamanBarangEntry(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = DestinasiBarangDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangDetail.barangId) { type = NavType.IntType })
        ) {
            HalamanBarangDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id -> navController.navigate("${DestinasiBarangEdit.route}/$id") }
            )
        }
        composable(
            route = DestinasiBarangEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangEdit.barangId) { type = NavType.IntType })
        ) {
            HalamanBarangEdit(navigateBack = { navController.popBackStack() })
        }

        // --- SECTION PEMINJAM ---
        composable(DestinasiPeminjamHome.route) {
            HalamanPeminjamHome(
                navigateBack = { navController.popBackStack() },
                navigateToEntry = { navController.navigate(DestinasiPeminjamEntry.route) },
                onDetailClick = { id -> navController.navigate("${DestinasiPeminjamDetail.route}/$id") }
            )
        }
        composable(DestinasiPeminjamEntry.route) {
            HalamanPeminjamEntry(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = DestinasiPeminjamDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamDetail.peminjamId) { type = NavType.IntType })
        ) {
            HalamanPeminjamDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id -> navController.navigate("${DestinasiPeminjamEdit.route}/$id") }
            )
        }
        composable(
            route = DestinasiPeminjamEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamEdit.peminjamId) { type = NavType.IntType })
        ) {
            HalamanPeminjamEdit(navigateBack = { navController.popBackStack() })
        }

        // --- SECTION KATEGORI ---
        composable(DestinasiKategoriHome.route) {
            HalamanKategoriHome(
                navigateToEntry = { navController.navigate(DestinasiKategoriEntry.route) },
                onDetailClick = { id -> navController.navigate("${DestinasiKategoriDetail.route}/$id") },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(DestinasiKategoriEntry.route) {
            HalamanKategoriEntry(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = DestinasiKategoriDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiKategoriDetail.kategoriId) { type = NavType.IntType })
        ) {
            HalamanKategoriDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id -> navController.navigate("${DestinasiKategoriEdit.route}/$id") }
            )
        }
        composable(
            route = DestinasiKategoriEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiKategoriEdit.kategoriId) { type = NavType.IntType })
        ) {
            HalamanKategoriEdit(navigateBack = { navController.popBackStack() })
        }
    }
}