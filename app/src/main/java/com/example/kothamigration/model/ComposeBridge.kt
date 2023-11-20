package com.example.kothamigration.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.SwitchModeScreen
import com.example.kothamigration.app.OnBoarding
import com.example.kothamigration.app.ProjectApp
import com.example.kothamigration.sellonboarding.SellFrame1
import com.example.kothamigration.sellonboarding.SellFrame2

class ComposeBridge : Fragment() {
    fun callMyComposeScreen(composeView: ComposeView, navController: NavController) {
        composeView.setContent {
            ProjectApp()

        }
    }

}

// OnBoarding_SellFrame.kt
@Composable
fun OnBoardingContent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding_screen_1"
    ) {
        composable("onboarding_screen_1") {
            SellFrame1 {
                // Navigate to the next screen when the user clicks "GetStarted"
                navController.navigate("onboarding_screen_2")
            }
        }
        composable("onboarding_screen_2") {
            SellFrame2 {
                navController.navigate("mode")
            }
        }
        composable("mode") {
            SwitchModeScreen(navController = navController)

        }
    }
}

