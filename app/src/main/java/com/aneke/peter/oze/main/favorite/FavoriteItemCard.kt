package com.aneke.peter.oze.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.aneke.peter.oze.data.Result
import java.util.*


@ExperimentalMaterialApi
@Composable
fun FavoriteCard(
    result: Result, openAction: (Result, Boolean) -> Unit,
    favoriteAction: (Result, Boolean) -> Unit
) {

    Card(
        onClick = {
            openAction.invoke(result, false)
        },
        modifier = Modifier
            .padding(8.dp, 16.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = rememberImagePainter(
                    data = result.avatar_url,
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                    }
                ),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(128.dp)
                    .padding(0.dp, 8.dp)
            )

            Column {
                Text(
                    text = result.login.capitalize(Locale.getDefault()),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
                IconButton(onClick = {
                    openAction.invoke(result, true)
                }) {
                    Icon(
                        Icons.Filled.OpenInBrowser,
                        contentDescription = "Go to Web",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            IconButton(onClick = {
                favoriteAction.invoke(result, result.isFavorite)
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                )
            }
        }

    }

}