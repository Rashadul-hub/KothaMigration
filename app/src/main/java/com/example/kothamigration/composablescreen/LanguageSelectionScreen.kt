package com.example.kothamigration.composablescreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.TitleText
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(
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
                        navController.navigate("") //Back to OTP Screen
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
                    TitleText(text = " ")
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
                        LanguageContents(dimensions, navController)
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
                LanguageContents(dimensions, navController)
            }
        }
    }

    }


@Composable
fun LanguageContents(
    dimensions: com.example.kothamigration.model.Dimensions,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background) //White BackGround Landscape Mode

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensions.small))


        // Logo Section
        Image(
            painter = painterResource(id = R.drawable.kotha_app_logo_large),
            contentDescription = "app logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(90.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        SelectionText(dimensions)

        Spacer(modifier = Modifier.height(dimensions.small))

        SelectOptions(navController)

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SelectionText(dimensions: com.example.kothamigration.model.Dimensions) {

    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {

        Text(
            text = stringResource(id = R.string.select_your_language),
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .alpha(1f),
            color = MaterialTheme.colorScheme.secondary, //Colors
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.inter_medium))
        )

        Spacer(modifier = Modifier.height(dimensions.small))

        Text(
            text = stringResource(id = R.string.select_your_interface),
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.scrim, //Colors
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.inter_medium))
        )
        Spacer(modifier = Modifier.height(dimensions.small))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)

//                .background(Color(0x1E455154))
                .background(MaterialTheme.colorScheme.outline)
        )

    }

}


@Composable
fun SelectOptions(navController: NavController) {
    val context = LocalContext.current // Get the context


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        ClickableOption("English (EN)", context, navController)

        Spacer(modifier = Modifier.width(5.dp)) // Add a Spacer to control the spacing

        ClickableOption("বাংলা (BN)", context, navController)
    }
}

@Composable
fun ClickableOption(optionText: String, context: Context, navController: NavController) {

    val isSelected by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.width(10.dp)) // Add padding from the start

    val configuration = Configuration(context.resources.configuration)
    val newLocale = if (optionText == "বাংলা (BN)") {
        Locale("bn") // Set to Bengali
    } else {
        Locale("en") // Set to English
    }
    configuration.setLocale(newLocale)

    Box(
        modifier = Modifier
            .clickable {
                // Change the app's language

                context.resources.updateConfiguration(
                    configuration,
                    context.resources.displayMetrics
                )

                // Navigate to the "login" screen
                navController.navigate("login")
            }
            .width(80.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(if (isSelected) Color(0xFF00B99F) else Color.LightGray)
    ) {
        Text(
            text = optionText,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .wrapContentSize(Alignment.Center), // Align text vertically and horizontally in the center
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_medium))
        )
    }
}


@Preview
@Composable
fun LanguageView() {
    val dummyNavController = rememberNavController() // Create a dummy NavController
    LanguageSelectionScreen(navController = dummyNavController)
}