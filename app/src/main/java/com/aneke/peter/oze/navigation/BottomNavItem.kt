package com.aneke.peter.oze.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Feed
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screenRoute:String) {

    object ResultList : BottomNavItem("Results", Icons.Filled.Feed, "results")
    object FavoriteList : BottomNavItem("Favorites", Icons.Filled.Favorite, "favorites")
}
