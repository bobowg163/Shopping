package dang.zhou.cun.shoping.ui.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 日期: 2025/6/1 时间: 12:56
* 作者: bobowg
* 备注：
*/


object PhotoEntryDestination : NavigationDestination {
    override val route: String
        get() = "photo_entry"
    override val titleRes: Int
        get() = R.string.add_photo

}

@Composable
fun PhotoEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: PhotoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
}