package com.example.kothamigration.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.kothamigration.sellonboarding.PostCaptionScreen
import com.example.kothamigration.sellonboarding.SelectUploadingVideo
import com.example.kothamigration.sellonboarding.SellFrame1
import com.example.kothamigration.sellonboarding.SellFrame2
import com.example.kothamigration.sellonboarding.SellFrame3
import com.example.kothamigration.sellonboarding.UploadedVideo
import com.example.kothamigration.sellonboarding.UploadingPreviewScreen

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
    var selectedItem by remember {
        mutableStateOf<String?>(null)
    }

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
                navController.navigate("video_sell_frame_screen_3")
            }
        }
        composable("video_sell_frame_screen_3") {
            SellFrame3{
                navController.navigate("video_sell_frame_screen_4.1")

            }

        }
        composable("video_sell_frame_screen_4.1") {
            SelectUploadingVideo{
                navController.navigate("video_sell_frame_screen_4.2")

            }

        }
        composable("video_sell_frame_screen_4.2") {
            UploadedVideo{
                navController.navigate("video_sell_frame_screen_5")

            }

        }
        composable("video_sell_frame_screen_5") {
            UploadingPreviewScreen{
                navController.navigate("video_sell_frame_screen_6")

            }

        }

        composable("video_sell_frame_screen_6") {
            PostCaptionScreen{
                navController.navigate("video_sell_frame_screen_7")

            }

        }




    }
}

