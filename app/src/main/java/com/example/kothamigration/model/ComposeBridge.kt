package com.example.kothamigration.model

import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import com.example.kothamigration.app.ProjectApp

class ComposeBridge {

    fun callMyComposeScreen(composeView: ComposeView, navController: NavController) {
        composeView.setContent {
            ProjectApp()
        }
    }
}