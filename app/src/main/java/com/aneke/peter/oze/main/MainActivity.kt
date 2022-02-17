package com.aneke.peter.oze.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.aneke.peter.oze.data.Result
import com.aneke.peter.oze.main.DetailLayout
import com.aneke.peter.oze.main.MainViewModel
import com.aneke.peter.oze.main.ResultCard
import com.aneke.peter.oze.navigation.BottomNavigator
import com.aneke.peter.oze.navigation.NavigationGraph
import com.aneke.peter.oze.ui.theme.BottomSheetShape
import com.aneke.peter.oze.ui.theme.OzeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@AndroidEntryPoint
@ExperimentalPagingApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {


    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OzeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreenView(mainViewModel)
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun MainScreenView(mainViewModel: MainViewModel){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigator(navController) }
    ) {
        NavigationGraph(navController, mainViewModel)
    }
}



