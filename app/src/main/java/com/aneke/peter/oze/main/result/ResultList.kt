package com.aneke.peter.oze.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.asFlow

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun ResultList(mainViewModel: MainViewModel, navController: NavController) {
    val lazyPostItems = mainViewModel.observeResults().collectAsLazyPagingItems()
    val uriHandler = LocalUriHandler.current

    Column {
        Text(
            text = "Search Results",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 34.sp,
            modifier = Modifier.padding(16.dp, 16.dp, 8.dp)
        )

        LazyColumn {
            items(lazyPostItems) { result ->
                if (result != null) {
                    ResultCard(result = result, openAction =
                    { item, openInWeb ->
                        if (openInWeb){
                            uriHandler.openUri(item.html_url)
                        } else {
                            mainViewModel.selectedResult = item
                            navController.currentBackStackEntry?.arguments?.putParcelable("result", item)
                            navController.navigate("detail")
                        }
                    }, favoriteAction = { item, isFavorite ->
                        if (isFavorite){
                            mainViewModel.deleteFavorite(item.copy(isFavorite = isFavorite.not()))
                        } else {
                            mainViewModel.insertFavorite(item.copy(isFavorite = isFavorite.not()))
                        }
                    })
                }
            }
        }
    }
}
