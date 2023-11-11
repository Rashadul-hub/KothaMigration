package com.example.kothamigration.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kothamigration.R
import com.example.kothamigration.homescreen.FeedScreen
import com.example.kothamigration.profilescreen.ProfilePageScreen
import com.example.kothamigration.profilescreen.ProfileSetUpScreen
import com.example.kothamigration.reelscreen.ReelScreen
import com.example.kothamigration.searchscreen.SearchScreen
import com.example.kothamigration.shopscreen.ShopScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = BottomNavRoutes.HOME.route) {

        composable(route = BottomNavRoutes.HOME.route) {
            FeedScreen()
        }
        composable(route = BottomNavRoutes.SEARCH.route) {
            SearchScreen(navHostController)
        }
        composable(route = BottomNavRoutes.REELS.route) {
            ReelScreen(navHostController)
        }
        composable(route = BottomNavRoutes.SHOP.route) {
            ShopScreen(navHostController)
        }
        composable(route = BottomNavRoutes.PROFILE.route) {
            ProfilePageScreen(navHostController)
        }
    }
}


/** Composable Bottom navigation Bar**/
@Composable
fun BottomNavbar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screenList = listOf(
        BottomNavScreens.Home,
        BottomNavScreens.Search,
        BottomNavScreens.Reels,
        BottomNavScreens.Notifications,
        BottomNavScreens.Profile,
    )
    BottomNavigation {
        screenList.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {

                    if (screen.route == BottomNavRoutes.PROFILE.route) {
                        CircularProfileView()
                    } else {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = " nav icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },


                //BottomNavigationBar Background Color
                modifier = Modifier.background(color = Color.White),

                selectedContentColor = Color(0xFF00B99F),
                unselectedContentColor = Color.Black
            )
        }
    }
}


@Composable
fun CircularProfileView() {
    Image(
        painter = painterResource(id = R.drawable.avatar),
        contentDescription = "current user",
        modifier = Modifier
            .size(25.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}
