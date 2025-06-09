package dang.zhou.cun.shoping.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.ui.AppViewModelProvider
import dang.zhou.cun.shoping.ui.PhotoTopAppBar
import dang.zhou.cun.shoping.ui.navigation.NavigationDestination
import dang.zhou.cun.shoping.ui.photo.formatedPrice
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kamal.aishwarya.weather.utils.DateUtil.toFormattedDate


object HomeDestination : NavigationDestination {
    override val route: String
        get() = "home"
    override val titleRes: Int
        get() = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToPhotoEntry: () -> Unit,
    navigateToPhotoUpdate: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PhotoTopAppBar(
                modifier = Modifier
                    .background(Color.Transparent),
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {

                val totalResult = homeUiState.photoList.sumOf { it.price }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        append("余额共计: ")
                        withStyle(
                            SpanStyle(
                                color = Color.Red,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$totalResult")
                        }
                        append(" 元")
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPhotoEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(
                    end = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(
                        LocalLayoutDirection.current
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_photo)
                )
            }
        }
    ) { paddingValues ->
        val photos = homeUiState.photoList
        ImageGrid(
            photos = photos,
            onPhotoClick = navigateToPhotoUpdate,
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() - 36.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
        )
    }
}

@Composable
private fun ImageGrid(
    photos: List<Photo>,
    onPhotoClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hazeState = rememberHazeState()
    var activePhotoId by rememberSaveable { mutableStateOf<Int?>(null) }
    if (photos.isEmpty()) {
        Text(
            text = stringResource(R.string.no_item_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxHeight()
                .hazeSource(hazeState),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(photos, key = { it.id }) { photo ->
                ImageItem(
                    photo,
                    Modifier.clickable {
                        activePhotoId = photo.id
                    },
                    onPhotoClick = onPhotoClick
                )
            }
        }

        if (activePhotoId != null) {
            FullScreenImage(
                photo = photos.first { it.id == activePhotoId },
                onDismiss = { activePhotoId = null }
            )
        }
    }

}

@Composable
fun ImageItem(
    photo: Photo,
    modifier: Modifier = Modifier,
    onPhotoClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(photo.url),
            contentDescription = photo.price.toString(),
            modifier = modifier
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable {
                    onPhotoClick(photo.id)
                },
            text = buildAnnotatedString {
                append(photo.formatedPrice())
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.surfaceDim,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 16.sp
                    )
                ) {
                    append(" 编辑")
                }
            },
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "开卡时间:",
                fontSize = 15.sp,
            )
            Text(
                text = photo.date.toFormattedDate(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

}


@Composable
private fun FullScreenImage(
    photo: Photo,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scrim(onDismiss, Modifier.fillMaxSize())
        ImageWithZoom(photo, Modifier.aspectRatio(1f))
    }
}

@Composable
private fun ImageWithZoom(photo: Photo, modifier: Modifier = Modifier) {
    var zoomed by remember { mutableStateOf(false) }
    var zoomOffset by remember { mutableStateOf(Offset.Zero) }
    Image(
        painter = painterResource(photo.url),
        contentDescription = null,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        zoomOffset = if (zoomed) Offset.Zero else calculateOffset(tapOffset, size)
                        zoomed = !zoomed
                    }
                )
            }
            .graphicsLayer {
                scaleX = if (zoomed) 2f else 1f
                scaleY = if (zoomed) 2f else 1f
                translationX = zoomOffset.x
                translationY = zoomOffset.y
            }
    )
}

private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}

@Composable
private fun Scrim(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val strClose = stringResource(R.string.close)
    Box(
        modifier = modifier
            .pointerInput(onClose) {
                detectTapGestures { onClose() }
            }
            .semantics(mergeDescendants = true) {
                contentDescription = strClose
                onClick {
                    onClose()
                    true
                }
            }
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            .background(Color.DarkGray.copy(alpha = 0.75f))
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShopCardPreview() {
    val photos = listOf(
        Photo(
            1,
            R.drawable.shoptitle,
            2.00,
            "2025-05-11"
        ),
        Photo(
            2,
            R.drawable.shopcontent1,
            2.30,
            "2025-05-11"
        ),
        Photo(
            3,
            R.drawable.shopconent,
            0.56,
            "2025-05-11"
        )
    )
    ShoppingTheme {
        ImageGrid(
            photos = photos,
            onPhotoClick = {
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopCardDarkPreview() {
    ShoppingTheme {
        ImageGrid(
            listOf(),
            onPhotoClick = {}
        )
    }
}