package dang.zhou.cun.shoping.ui.photo

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.PhotoTopAppBar
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import kamal.aishwarya.weather.utils.DateUtil.toFormattedDate
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(
    navigateToEditPhoto: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PhotoTopAppBar(
                title = stringResource(PhotoDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditPhoto(uiState.value.photoDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(
                    end = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateEndPadding(LocalLayoutDirection.current)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_photo_title)
                )
            }
        },
        modifier = modifier

    ) { innerPadding ->
        PhotoEditBody(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState()),
            photoDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deletePhoto()
                    navigateBack()
                }
            }
        )
    }
}

@Composable
private fun PhotoEditBody(
    modifier: Modifier = Modifier,
    photoDetailsUiState: PhotoDetailsUiState,
    onDelete: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        PhotoDetails(
            photo = photoDetailsUiState.photoDetails.toPhoto(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                modifier = Modifier.padding(16.dp),
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
//                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
    }
}


@Composable
private fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.note)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}

@Composable
fun PhotoDetails(
    photo: Photo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PhotoDetailsRow(
                labelResID = R.string.photo_item,
                photoDetail = photo.url.toString(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            PhotoDetailsRow(
                labelResID = R.string.photo_price,
                photoDetail = photo.formatedPrice(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            PhotoDetailsRow(
                labelResID = R.string.photo_date,
                photoDetail = photo.date.toFormattedDate(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }
    }

}

@Composable
private fun PhotoDetailsRow(
    @StringRes labelResID: Int,
    photoDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = photoDetail, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoEditBodyPreview() {
    ShoppingTheme {
        PhotoEditBody(
            photoDetailsUiState = PhotoDetailsUiState(
                outOfStock = true,
                photoDetails = PhotoDetails(
                    id = 1,
                    url = R.drawable.shoptitle,
                    price = "10.0",
                    date = "2023-01-01"
                )
            ),
            onDelete = {},
        )
    }
}
