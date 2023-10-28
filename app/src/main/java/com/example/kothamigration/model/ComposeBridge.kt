package com.example.kothamigration.model

import androidx.compose.ui.platform.ComposeView
import com.example.kothamigration.composablescreen.MyComposeScreen

class ComposeBridge {

    fun callMyComposeScreen(composeView: ComposeView) {
        composeView.setContent {
            MyComposeScreen()
        }
    }
}