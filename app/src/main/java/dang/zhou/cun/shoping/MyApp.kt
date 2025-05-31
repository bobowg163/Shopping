package dang.zhou.cun.shoping

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dang.zhou.cun.shoping.ui.navigation.InventoryNavHost
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {
    ShoppingTheme {
        InventoryNavHost(navController = navController)
    }
}
