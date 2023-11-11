package com.example.kothamigration.shopscreen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kothamigration.composablefunctions.TitleText
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    navController: NavController,

    ) {

    val windowSize = rememberWindowSizeClass()
    val dimensions = when (windowSize.width) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        is WindowSize.Large -> largeDimensions
    }

    // Determine whether the device is in landscape orientation
    val isLandscape = windowSize.width is WindowSize.Large

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = dimensions.large


            ) {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back Icon",
                            Modifier
                                .size(32.dp)
                                .padding(1.dp)
                        )
                    }
                }, title = {
                    TitleText(text = "Shopping")
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
                            .background(color = MaterialTheme.colorScheme.background) //White BackGround Landscape Mode
                    ) {
                        // Body Section
                        ShopContents()
                    }
                }
            }
        } else {
            // In portrait mode, we use this existing layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .background(color = MaterialTheme.colorScheme.background) // White BackGround Color Portrait Mode
            ) {

                // Body Section
                ShopContents()
            }
        }
    }

}

@Composable
fun ShopContents() {

    Box (modifier = Modifier.fillMaxSize().background(Color.Cyan)){

    }



}
