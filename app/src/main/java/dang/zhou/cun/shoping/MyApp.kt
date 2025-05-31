package dang.zhou.cun.shoping

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Shop2
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dang.zhou.cun.shoping.ui.ShopCard
import dang.zhou.cun.shoping.ui.theme.Purple40
import dang.zhou.cun.shoping.ui.theme.ShoppingTheme
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MyApp() {
    ShoppingTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shop2,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "东岸超市购物",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                )

            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
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
                                append("2000")
                            }
                            append(" 元")
                        }
                    )
                }
            },
            floatingActionButton = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                        tint = Purple40,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = Purple40,
                                shape = CircleShape
                            )
                    )
                }
            }
        ) { paddingValues ->
            ShopCard(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    MyApp()
}