package com.example.kothamigration.composablescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.ResendButton
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.R
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(navController: NavController) {


    val windowSize = rememberWindowSizeClass()
    val dimensions = when (windowSize.width) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        is WindowSize.Large -> largeDimensions
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
                        navController.navigate("login") //Back to Login Screen
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
                    SignInTitle(text = stringResource(id = R.string.sign_in))
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
                        OtpContents(dimensions, navController)
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
                OtpContents(dimensions, navController)
            }
        }
    }
}


@Composable
fun OtpContents(dimensions: Dimensions, navController: NavController) {
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
        VerifySection()
        Spacer(modifier = Modifier.height(dimensions.medium))
        SMSInformationSection()
        Spacer(modifier = Modifier.height(dimensions.medium))
        InputOTPNumber()
        Spacer(modifier = Modifier.height(dimensions.medium))
        ConfirmButton(navController)
        Spacer(modifier = Modifier.height(dimensions.mediumLarge))
        ContactSupportText()
    }
}


@Composable
fun VerifySection() {
    val text = stringResource(id =  R.string.otp_verification)

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        fontWeight = FontWeight(700),
        color = MaterialTheme.colorScheme.secondary, // Heading Text Color
        fontFamily = FontFamily(Font(R.font.inter_bold))

    )
}


@Composable
fun SMSInformationSection() {
    Text(
        text = stringResource(id = R.string.sms_sent),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth() //Take the full available width
            .wrapContentHeight(),// Wrap the content for height
        color = MaterialTheme.colorScheme.scrim, // Hint Color
        fontWeight = FontWeight(600),
        fontFamily = FontFamily(Font(R.font.inter_semi_bold))
    )
}

@Composable
fun InputOTPNumber() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Enter OTP Code
        Box(
            modifier = Modifier
                .width(175.dp)
                .height(40.dp)
                .padding(horizontal = 8.dp)
                .background(Color.Transparent) //White BackGround
                .border(1.dp, color = MaterialTheme.colorScheme.onPrimary) //Black Border
        )

        ResendButton(
            buttonText = stringResource(id = R.string.resend_in_seconds),
            onClick = {})//Resend Button
    }
}


@Composable
fun ConfirmButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.attempts_left),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(8.dp)) // Add vertical spacing

            CustomButton(buttonText = stringResource(id = R.string.confirm)) {
                ///used FOr Demo Purpose
                navController.navigate("terms")
            }
        }
    }
}


@Composable
fun ContactSupportText() {

    val waitAndPressString = stringResource(R.string.wait_and_press)
    val contactSupportString = stringResource(R.string.contact_support)
    val emailString = stringResource(R.string.info_kotha_app_email)


    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.scrim)) {
            append("$waitAndPressString \n$contactSupportString")
        }
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.scrim
            )
        ) {
            append(" ", emailString)
        }
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        fontWeight = FontWeight(600),
        fontFamily = FontFamily(Font(R.font.inter_semi_bold))
    )
}

@Preview
@Composable
fun OTPView() {
    val dummyNavController = rememberNavController() // Create a dummy NavController
    OtpScreen(navController = dummyNavController)
}

