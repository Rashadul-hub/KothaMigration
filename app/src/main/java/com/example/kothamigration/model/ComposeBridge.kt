package com.example.kothamigration.model

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.sellonboarding.PostCaptionScreen
import com.example.kothamigration.sellonboarding.SelectUploadingVideo
import com.example.kothamigration.sellonboarding.SellFrame1
import com.example.kothamigration.sellonboarding.SellFrame2
import com.example.kothamigration.sellonboarding.SellFrame3
import com.example.kothamigration.sellonboarding.SellFrameAlertBox
import com.example.kothamigration.sellonboarding.UploadedVideo
import com.example.kothamigration.sellonboarding.UploadingPreviewScreen
import com.example.kothamigration.sellonboarding.VideoPriceScreen
import com.example.kothamigration.sellonboarding.VideoSellFrame8


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

        composable("video_sell_frame_screen_7") {
            VideoPriceScreen{
                navController.navigate("video_sell_frame_screen_8")
            }
        }
        composable("video_sell_frame_screen_8") {
            VideoSellFrame8{
                navController.navigate("video_sell_frame_screen_9")
            }
        }
        composable("video_sell_frame_screen_9") {
            SellFrameAlertBox{
                navController.navigate("")

            }
        }

    }
}

