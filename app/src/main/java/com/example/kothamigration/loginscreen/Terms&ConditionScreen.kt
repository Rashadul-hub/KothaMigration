package com.example.kothamigration.loginscreen

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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionScreen(navController: NavController) {


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
                        navController.navigate("otp") //back to Login Screen
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
                    SignInTitle(text = stringResource(id = R.string.terms_and_condition))
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
                        PolicyContents(dimensions, navController)
                    }
                }
            }
        } else {
            // In portrait mode, we  use this existing layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .background(color = MaterialTheme.colorScheme.background) // White BackGround Color Portrait Mode
            ) {

                // Body Section
                PolicyContents(dimensions, navController)
            }
        }
    }
}

@Composable
fun PolicyContents(dimensions: com.example.kothamigration.model.Dimensions, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1.5f))
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
        Spacer(modifier = Modifier.weight(0.5f))

        Spacer(modifier = Modifier.height(dimensions.small))

        TermsAgreedButton(dimensions = dimensions, navController = navController)
        Spacer(modifier = Modifier.weight(1f))


    }
}


@Composable
fun TermsAgreedButton(dimensions: com.example.kothamigration.model.Dimensions, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){

            Spacer(modifier = Modifier.height(dimensions.large))

            TermsAndConditionInstruction()

            Spacer(modifier = Modifier.height(dimensions.small))

            CustomButton(buttonText = stringResource(id = R.string.agree_and_continue)) {
                ///used FOr Demo Purpose
                navController.navigate("dialog")
            }


        }

    }

}



@Composable
fun TermsAndConditionInstruction() {

    val _read_our = stringResource(R.string.read_our)
    val _privacy_policy = stringResource(R.string.privacy_policy)
    val _agree_and_continue = stringResource(R.string.agree_continue)
    val termsOfService = stringResource(id = R.string.term_of_service)

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(_read_our," ")
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { // Set the color to green
            append(_privacy_policy)
        }

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) { // Set the color to green
            append(" ",_agree_and_continue," ")
        }

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { // Set the color to green
            append(termsOfService)
        }
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_medium))
    )
}


@Preview
@Composable
fun Terms() {
    val dummyNavController = rememberNavController() // Create a dummy NavController

    TermsAndConditionScreen(navController = dummyNavController)
}
