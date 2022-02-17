package com.aneke.peter.oze.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.aneke.peter.oze.data.Result
import java.util.*

@ExperimentalPagingApi
@Composable
fun DetailLayout(viewModel: MainViewModel, navController: NavController, result: Result?) {

    val item = result ?: viewModel.selectedResult
    val uriHandler = LocalUriHandler.current

    item?.let { resultItem ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "back",
                )
            }

            Image(
                painter = rememberImagePainter(
                    data = resultItem.avatar_url,
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                    }
                ),
                contentDescription = "Avatar",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .padding(0.dp, 8.dp)
            )

            Text(
                text = resultItem.login.capitalize(Locale.getDefault()),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                modifier = Modifier.padding(32.dp, 16.dp, 0.dp, 16.dp)
            )


            Button(
                onClick = {
                    uriHandler.openUri(resultItem.html_url)
                }, modifier = Modifier.padding(32.dp, 16.dp, 0.dp, 16.dp)
            ) {
                Text(
                    text = "Open In Browser",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            if (resultItem.isFavorite){
                Image(Icons.Default.Favorite, contentDescription = "favorite", modifier = Modifier.size(100.dp) )
            }

            Text(
                text = "Profile Score: ${resultItem.score}",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(32.dp, 8.dp, 0.dp, 8.dp)
            )

            Text(
                text = "Profile Type: ${resultItem.type}",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(32.dp, 8.dp, 0.dp, 8.dp)
            )
        }

    }
}