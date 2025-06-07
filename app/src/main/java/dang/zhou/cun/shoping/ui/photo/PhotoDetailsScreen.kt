package dang.zhou.cun.shoping.ui.photo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.AppViewModelProvider

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui.photo
* 作者: bobowg
* 日期: 2025/6/7 时间: 17:43
* 备注:
*/

object PhotoDetailsDestination : NavigationDestination {
    override val route: String
        get() = "photo_details"
    override val titleRes: Int
        get() = R.string.photo_detail_title
    const val photoIdArg = "photoId"
    val routeWithArgs = "$route/{$photoIdArg}"

}

@Composable
fun PhotoDetailsScreen(
    navigateToEditPhoto: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

}