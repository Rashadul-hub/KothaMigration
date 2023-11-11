package com.example.kothamigration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kothamigration.composablefunctions.ThemeSwitch
import com.example.kothamigration.composablefunctions.TitleText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchModeScreen(
    navController: NavController,

) {


    val windowSize = com.example.kothamigration.model.rememberWindowSizeClass()
    val dimensions = when (windowSize.width) {
        is com.example.kothamigration.model.WindowSize.Small -> com.example.kothamigration.model.smallDimensions
        is com.example.kothamigration.model.WindowSize.Compact -> com.example.kothamigration.model.compactDimensions
        is com.example.kothamigration.model.WindowSize.Medium -> com.example.kothamigration.model.mediumDimensions
        is com.example.kothamigration.model.WindowSize.Large -> com.example.kothamigration.model.largeDimensions
    }

    // Determine whether the device is in landscape orientation
    val isLandscape = windowSize.width is com.example.kothamigration.model.WindowSize.Large

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                elevation = dimensions.large
            ) {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("otp") //Back to OTP Screen
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back Icon",
                            Modifier
                                .size(32.dp)
                                .padding(1.dp)
                        )
                    }
                }, title = {
                    TitleText(text = "Mode Switching")
                })
            }
        },
    ) { values ->
        // If in landscape mode, wrap the content in a scrollable LazyColumn
        if (isLandscape) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(values)
                            .background(color = MaterialTheme.colorScheme.background) // White Background Color
                    ) {
                        // Body Section
                        ModeContents(dimensions)

                    }
                }
            }
        } else {
            // In portrait mode, we  use this existing layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .background(color = MaterialTheme.colorScheme.background) // White Background Color
            ) {

                // Body Section
                ModeContents(dimensions)
            }
        }
    }
}

@Composable
fun ModeContents(dimensions: com.example.kothamigration.model.Dimensions) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)


    ) {

        Spacer(modifier = Modifier.height(dimensions.medium))

        // Logo Section
        Image(
            painter = painterResource(id = R.drawable.kotha_app_logo_small),
            contentDescription = "app logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(40.dp)
        )


        Spacer(modifier = Modifier.height(dimensions.smallMedium))

        Spacer(modifier = Modifier.height(dimensions.large))

        ThemeSwitch()

        Spacer(modifier = Modifier.height(dimensions.smallMedium))




    }
}
