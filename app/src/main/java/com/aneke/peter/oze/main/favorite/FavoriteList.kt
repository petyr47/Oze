package com.aneke.peter.oze.main

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi


@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun FavoriteList(mainViewModel: MainViewModel, navController: NavController) {

    mainViewModel.fetchFavorites()

    val favorites = mainViewModel.favorites.observeAsState(emptyList())
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Column(Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Favorites",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 34.sp,
                modifier = Modifier.padding(16.dp, 16.dp, 8.dp))


            Button(onClick = {
                mainViewModel.clearFavorites()
                Toast.makeText(context, "Favorites Cleared", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.padding(8.dp, 16.dp)
            ) {
                Text(
                    text = "Clear All",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp)
            }
        }


        LazyColumn{
            items(favorites.value){ favorites ->
                FavoriteCard(result = favorites.toResult(), openAction =
                { item, openInWeb ->
                    if (openInWeb){
                        uriHandler.openUri(item.html_url)
                    } else {
                        navController.currentBackStackEntry?.arguments?.putParcelable("result", item)
                        navController.navigate("detail")
                    }
                }, favoriteAction = { item, _ ->
                    mainViewModel.deleteFavorite(item)
                }
                )
            }

        }

    }
}