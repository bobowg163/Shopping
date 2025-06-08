package dang.zhou.cun.shoping.ui.photo

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.PhotoTopAppBar
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PhotoTopAppBar(
                title = stringResource(PhotoEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        PhotoEntryBody(
            photoUiState = viewModel.photoUiState,
            onPhotoValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePhoto()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
                .verticalScroll(rememberScrollState())
        )
    }
}
