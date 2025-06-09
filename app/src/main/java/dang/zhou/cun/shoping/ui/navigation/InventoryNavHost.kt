package dang.zhou.cun.shoping.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dang.zhou.cun.shoping.ui.home.HomeDestination
import dang.zhou.cun.shoping.ui.home.HomeScreen
import dang.zhou.cun.shoping.ui.photo.PhotoDetailsDestination
import dang.zhou.cun.shoping.ui.photo.PhotoDetailsScreen
import dang.zhou.cun.shoping.ui.photo.PhotoEditDestination
import dang.zhou.cun.shoping.ui.photo.PhotoEditScreen
import dang.zhou.cun.shoping.ui.photo.PhotoEntryDestination
import dang.zhou.cun.shoping.ui.photo.PhotoEntryScreen

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.navigation
* 日期: 2025/5/31 时间: 16:19
* 作者: bobowg
* 备注：
*/

@Composable
fun InventoryNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPhotoEntry = { navController.navigate(PhotoEntryDestination.route) },
                navigateToPhotoUpdate = {
                    navController.navigate("${PhotoEditDestination.route}/${it}")
                }
            )
        }
        composable(route = PhotoEntryDestination.route) {
            PhotoEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = PhotoDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PhotoDetailsDestination.photoIdArg) {
                type = NavType.IntType
            })
        ) {
            PhotoDetailsScreen(
                navigateToEditPhoto = { navController.navigate("${PhotoEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = PhotoEditDestination.routeWithArgs,
            arguments = listOf(navArgument(PhotoEditDestination.photoIdArg) {
                type = NavType.IntType
            })
        ) {
            PhotoEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}


