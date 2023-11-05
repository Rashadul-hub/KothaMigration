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
import com.example.kothamigration.composablefunctions.AlertDialog
import com.example.kothamigration.composablescreen.LanguageSelectionScreen
import com.example.kothamigration.composablescreen.LoginScreen
import com.example.kothamigration.composablescreen.OtpScreen
import com.example.kothamigration.composablescreen.ProfileScreen
import com.example.kothamigration.composablescreen.SwitchModeScreen
import com.example.kothamigration.composablescreen.TermsAndConditionScreen
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.theme.KothaTheme

/**This File Contains All The Screens**/

var darkMode by mutableStateOf(false) //For// Switching Theme Dark -> Light Mode

@Composable
fun ProjectApp() {


    val window = rememberWindowSizeClass()
    val navController = rememberNavController()

    KothaTheme(window, darkTheme = darkMode) {

        Surface(modifier = Modifier.fillMaxSize()) {

            NavHost(navController = navController,startDestination = "language") {

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
                composable("mode") {
                   SwitchModeScreen(navController = navController)
                }
                composable("profile"){
                    ProfileScreen(navController = navController)
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
