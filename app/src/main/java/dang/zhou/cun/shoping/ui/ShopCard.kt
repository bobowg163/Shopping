package dang.zhou.cun.shoping.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.data.Photo
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState


@Composable
fun ShopCard(modifier: Modifier = Modifier) {
    val photos = listOf(
        Photo(
            1,
            R.drawable.shoptitle,
            "正面",
            "2025-5-1"

        ),
        Photo(
            2,
            R.drawable.shopconent,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
        Photo(
            3,
            R.drawable.shopcontent1,
            "背面",
            "2025-5-1"
        ),
    )

    ImageGrid(
        photos, modifier.fillMaxWidth().padding(top = 36.dp)
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
private fun ImageGrid(photos: List<Photo>, modifier: Modifier = Modifier) {
    val hazeState = rememberHazeState()
    var activePhotoId by rememberSaveable { mutableStateOf<Int?>(null) }
    LazyVerticalGrid(
        modifier = modifier.fillMaxHeight().hazeSource(hazeState),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(photos) { photo ->
            ImageItem(
                photo,
                Modifier.clickable { activePhotoId = photo.id }
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

@Composable
private fun ImageItem(photo: Photo, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(photo.url),
            contentDescription = photo.title,
            modifier = modifier.aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = photo.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = photo.date,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
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
    val state = rememberHazeState()
    ShoppingTheme {
        ShopCard(modifier = Modifier.padding(top = 16.dp))
    }
}