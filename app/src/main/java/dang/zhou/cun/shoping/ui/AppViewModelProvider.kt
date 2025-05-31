package dang.zhou.cun.shoping.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Shop2
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dang.zhou.cun.shoping.PhotoApplication
import dang.zhou.cun.shoping.R
import dang.zhou.cun.shoping.ui.home.HomeViewModel

/*
* 项目名称: Shopping
* 包名: dang.zhou.cun.shoping.ui
* 日期: 2025/5/31 时间: 22:17
* 作者: bobowg
* 备注：
*/


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(PhotoApplication().container.photoRepository)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior?= null,
    navigateUp:()-> Unit ={}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
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
        },
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}