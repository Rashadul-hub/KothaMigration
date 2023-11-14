package com.example.kothamigration.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.SwitchModeScreen
import com.example.kothamigration.composablefunctions.AlertDialog
import com.example.kothamigration.homescreen.BottomNavigationBar
import com.example.kothamigration.homescreen.FeedScreen
import com.example.kothamigration.loginscreen.LanguageSelectionScreen
import com.example.kothamigration.loginscreen.LoginScreen
import com.example.kothamigration.loginscreen.OtpScreen
import com.example.kothamigration.loginscreen.TermsAndConditionScreen
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.profilescreen.ProfileSetUpScreen
import com.example.kothamigration.sellonboarding.Demo2
import com.example.kothamigration.theme.KothaTheme

/**This File Contains All The Screens**/

var darkMode by mutableStateOf(false) //For// Switching Theme Dark -> Light Mode

@Composable
fun ProjectApp() {

    val window = rememberWindowSizeClass()
    val navController = rememberNavController()

    KothaTheme(window, darkTheme = darkMode) {

        Surface(modifier = Modifier.fillMaxSize()) {

            

            NavHost(navController = navController, startDestination = "sell") {

                composable("language") {
                    LanguageSelectionScreen(navController = navController)
                }
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("otp") {
                    OtpScreen(navController = navController)
                }
                composable("terms") {
                    TermsAndConditionScreen(navController = navController)
                }
                composable("dialog") {
                    AlertDialog(navController = navController)
                }
                composable("profilesetup") {
                    ProfileSetUpScreen(navController = navController)
                }
                composable("feed") {
                    FeedScreen()
                    BottomNavigationBar()
                }
                composable("mode") {
                    SwitchModeScreen(navController = navController)
                }
                composable("sell"){
                    Demo2()
                }
            }

        }

    }
}


@Preview
@Composable
fun ProjectPreview() {
    ProjectApp()
}
