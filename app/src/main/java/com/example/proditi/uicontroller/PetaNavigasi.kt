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
import com.example.proditi.uicontroller.view.barang.HalamanBarangDetail
import com.example.proditi.uicontroller.view.barang.HalamanBarangEdit
import com.example.proditi.uicontroller.view.barang.HalamanBarangHome
import com.example.proditi.uicontroller.view.barang.*
import com.example.proditi.uicontroller.view.peminjam.HalamanPeminjamDetail
import com.example.proditi.uicontroller.view.peminjam.HalamanPeminjamEdit
import com.example.proditi.uicontroller.view.peminjam.HalamanPeminjamEntry
import com.example.proditi.uicontroller.view.peminjam.HalamanPeminjamHome
import com.example.proditi.uicontroller.view.HalamanDetail


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiDashboard.route,
        modifier = modifier
    ) {
        // --- DASHBOARD (Sesuai dengan kode Anda) ---
        composable(DestinasiDashboard.route) {
            HalamanDashboard(
                onMenuClick = { route ->
                    navController.navigate(route)
                }
            )
        }

        // --- SECTION KATEGORI (Fitur Baru) ---
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

        // =========================================================
        // 3. MODUL BARANG (Tambahkan ini agar tidak Crash)
        // =========================================================

        // Home Barang
        composable(DestinasiBarangHome.route) {
            HalamanBarangHome(
                navigateBack = { navController.popBackStack() },
                navigateToEntry = { navController.navigate(DestinasiBarangEntry.route) },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiBarangDetail.route}/$id")
                }
            )
        }

        // Entry Barang
        composable(DestinasiBarangEntry.route) {
            HalamanBarangEntry(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Barang
        composable(
            route = DestinasiBarangDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangDetail.barangId) {
                type = NavType.IntType
            })
        ) {
            HalamanBarangDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id ->
                    navController.navigate("${DestinasiBarangEdit.route}/$id")
                }
            )
        }

        // Edit Barang
        composable(
            route = DestinasiBarangEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiBarangEdit.barangId) {
                type = NavType.IntType
            })
        ) {
            HalamanBarangEdit(
                navigateBack = { navController.popBackStack() }
            )
        }

        // =========================================================
        // 4. MODUL PEMINJAM (Tambahkan juga jika belum ada)
        // =========================================================
        composable(DestinasiPeminjamHome.route) {
            HalamanPeminjamHome(
                navigateBack = { navController.popBackStack() },
                navigateToEntry = { navController.navigate(DestinasiPeminjamEntry.route) },
                onDetailClick = { id ->
                    // Mengirim ID ke halaman detail
                    navController.navigate("${DestinasiPeminjamDetail.route}/$id")
                }
            )
        }

        // 2. ENTRY PEMINJAM
        composable(DestinasiPeminjamEntry.route) {
            HalamanPeminjamEntry(
                navigateBack = { navController.popBackStack() }
            )
        }

        // 3. DETAIL PEMINJAM (Pastikan arguments-nya benar)
        composable(
            route = DestinasiPeminjamDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamDetail.peminjamId) {
                type = NavType.IntType
            })
        ) {
            HalamanPeminjamDetail(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { id ->
                    navController.navigate("${DestinasiPeminjamEdit.route}/$id")
                }
            )
        }

        // 4. EDIT PEMINJAM
        composable(
            route = DestinasiPeminjamEdit.routeWithArgs,
            arguments = listOf(navArgument(DestinasiPeminjamEdit.peminjamId) {
                type = NavType.IntType
            })
        ) {
            HalamanPeminjamEdit(
                navigateBack = { navController.popBackStack() }
            )
        }



        composable(
            route = DestinasiEdit.routeWithArgs, // Gunakan routeWithArgs
            arguments = listOf(navArgument(DestinasiEdit.peminjamanId) {
                type = NavType.IntType
            })
        ) {
            // Panggil Halaman Edit Peminjaman di sini
            // Pastikan Anda sudah membuat composable HalamanEditPeminjaman
            HalamanEdit(
                navigateBack = { navController.popBackStack() },
                // onUpdateSuccess = { navController.popBackStack() } // Opsional jika ada
            )
        }

        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.peminjamanId) {
                type = NavType.IntType
            })
        ) {
            HalamanDetail(
                navigateBack = { navController.popBackStack() },

                // PERHATIKAN INI:
                navigateToEdit = { id ->
                    // Harus: "edit_peminjaman/123"
                    navController.navigate("${DestinasiEdit.route}/$id")
                }
            )

        }
    }
}