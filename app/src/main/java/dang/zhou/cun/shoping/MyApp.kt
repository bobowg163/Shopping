package dang.zhou.cun.shoping

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dang.zhou.cun.shoping.ui.navigation.InventoryNavHost
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme

@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {
    ShoppingTheme {
        InventoryNavHost(navController = navController)
    }
}
