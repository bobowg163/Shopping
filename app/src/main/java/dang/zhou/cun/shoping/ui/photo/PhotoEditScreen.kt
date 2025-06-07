package dang.zhou.cun.shoping.ui.photo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 作者: bobowg
* 日期: 2025/6/7 时间: 23:33
* 备注:
*/


object PhotoEditDestination : NavigationDestination {
    override val route = "photo_edit"
    override val titleRes = R.string.edit_photo_screen
    const val photoIdArg = "photoId"
    val routeWithArgs = "$route/{$photoIdArg}"
}

@Composable
fun PhotoEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

}