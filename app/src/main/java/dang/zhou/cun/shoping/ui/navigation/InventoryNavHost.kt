package dang.zhou.cun.shoping.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dang.zhou.cun.shoping.MyApp
import dang.zhou.cun.shoping.ui.home.HomeDestination
import dang.zhou.cun.shoping.ui.home.HomeScreen

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
        modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.route
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToPhotoEntry = {navController.navigate(PhotoEntryDestination.route)},
                navigateToPhotoUpdate = { navController.navigate("${PhotoEntryDestination.route}/${it}")}
            )
        }
    }
}


