package dang.zhou.cun.shoping.ui.photo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.PhotoTopAppBar
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: PhotoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PhotoTopAppBar(
                title = stringResource(PhotoEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        PhotoEntryBody(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(
                        LocalLayoutDirection.current
                    ),
                    top = 132.dp,
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            photoUiState = viewModel.photoUiState,
            onPhotoValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePhoto()
                    navigateBack()
                }
            }
        )
    }
}

@Composable
fun PhotoEntryBody(
    modifier: Modifier = Modifier,
    photoUiState: PhotoUiState,
    onPhotoValueChange: (PhotoDetails) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PhotoInputFrom(
            photoDetails = photoUiState.photoDetails,
            onPhotoValueChange = onPhotoValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = photoUiState.isEntryValid,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.save_photo)
            )
        }
    }
}

@Composable
fun PhotoInputFrom(
    modifier: Modifier = Modifier,
    photoDetails: PhotoDetails,
    onPhotoValueChange: (PhotoDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val radioOptions = listOf("封面", "吴", "麦")
        @DrawableRes val url: Int = radioButtonSingleSelection(
            radioOptions = radioOptions
        )

        OutlinedTextField(
            value =url.toString(),
            onValueChange = {
                onPhotoValueChange(photoDetails.copy(url = url))
            },
            label = {
                Text(text = stringResource(R.string.photo_item))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = enabled,
            singleLine = true
        )


        OutlinedTextField(
            value = photoDetails.price,
            onValueChange = {
                onPhotoValueChange(photoDetails.copy(price = it))
            },
            label = {
                Text(
                    text = stringResource(R.string.photo_price)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = photoDetails.date,
            onValueChange = {
                onPhotoValueChange(photoDetails.copy(date = it))
            },
            label = {
                Text(
                    text = stringResource(R.string.photo_date)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields)
            )
        }
    }
}

//添加一个单选按钮
@Composable
@DrawableRes
fun radioButtonSingleSelection(
    modifier: Modifier = Modifier,
    radioOptions: List<String>,
): Int {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        radioOptions.forEach { text ->
            Row(
                modifier = modifier
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }

    return when (selectedOption) {
        "封面" -> {
            R.drawable.shoptitle
        }

        "吴" -> {
            R.drawable.shopconent
        }

        "麦" -> {
            R.drawable.shopcontent1
        }

        else -> {
            R.drawable.shoptitle
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PhotoEntryBodyPreview() {
    ShoppingTheme {
        PhotoEntryBody(
            modifier = Modifier.padding(top = 32.dp),
            photoUiState = PhotoUiState(
                photoDetails = PhotoDetails(
                    url = R.drawable.shoptitle,
                    price = "2032.00",
                    date = "2025-12-14"
                ),
                true
            ),
            onSaveClick = {},
            onPhotoValueChange = {}
        )
    }
}