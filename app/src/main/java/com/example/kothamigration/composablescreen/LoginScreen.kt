package com.example.kothamigration.composablescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.model.ComposeBridge
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {



    val windowSize = com.example.kothamigration.model.rememberWindowSizeClass()
    val dimensions = when (windowSize.width) {
        is WindowSize.Small -> com.example.kothamigration.model.smallDimensions
        is WindowSize.Compact -> com.example.kothamigration.model.compactDimensions
        is WindowSize.Medium -> com.example.kothamigration.model.mediumDimensions
        is WindowSize.Large -> com.example.kothamigration.model.largeDimensions
    }
    // Determine whether the device is in landscape orientation
    val isLandscape = windowSize.width is WindowSize.Large

    Scaffold(
        topBar = {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                elevation = dimensions.large,
            ) {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("language") //back to LanguageSelection Screen
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
                        LoginContent(dimensions, navController)
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
                LoginContent(dimensions, navController )
            }
        }
    }
}

@Composable
fun LoginContent(
    dimensions: Dimensions,
    navController: NavController,



) {

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
        TitleSection()
        Spacer(modifier = Modifier.height(dimensions.medium))
        ExampleText()
        Spacer(modifier = Modifier.height(dimensions.medium))
        PhoneNumberInput()
        Spacer(modifier = Modifier.height(dimensions.mediumLarge))
        SendOTPButton( navController)
        Spacer(modifier = Modifier.height(dimensions.mediumLarge))
        RegisterLink()
    }
}


@Composable
fun TitleSection() {

    val typeYour11Digit = stringResource(R.string.type_your_11_digit)
    val bangladeshi = stringResource(R.string.bangladeshi)
    val number = stringResource(R.string.number)


    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(typeYour11Digit, " ")
        }
        withStyle(style = SpanStyle(color = Color(0xFF006115))) { // Set the color to green
            append(bangladeshi, " ")
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(number)
        }
    }

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_bold))
    )
}


@Composable
fun ExampleText() {
    Text(
        text = stringResource(id = R.string.example),
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth() //Take the full available width
            .wrapContentHeight(),// Wrap the content for height
        color = MaterialTheme.colorScheme.scrim, //Hint Color
        fontWeight = FontWeight(600),
        fontFamily = FontFamily(Font(R.font.inter_semi_bold))
    )
}


@Composable
fun PhoneNumberInput() {
    var phoneNumber by remember {
        mutableStateOf("")
    }

    Box(
        contentAlignment = Alignment.Center, // Center the content horizontally and vertically
        modifier = Modifier
            .fillMaxWidth() // Take the full available width
            .padding(horizontal = 16.dp) // Add horizontal padding
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                modifier = Modifier
                    .fillMaxWidth() // Take the full available width within the Box
                    .widthIn(max = 333.dp) // maximum width
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline // Inner Border Color
                    ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onPrimary,// Regular Black Color
                    textAlign = TextAlign.Center,
                    letterSpacing = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semi_bold))
                )
            )
            // Display error message text when isError is true
            Text(
                text = stringResource(id = R.string.error_phone_number_text),
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 6.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun SendOTPButton( navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        CustomButton(buttonText = stringResource(id = R.string.send_otp)) {

            navController.navigate("otp") // Navigate to the OTP screen
        }


    }

}




@Composable
fun RegisterLink() {
    Text(
        text = stringResource(id = R.string.dont_have_bangladeshi_number_tap_here),
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        textDecoration = TextDecoration.Underline,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth() // Take the full available width
            .wrapContentHeight(), // Wrap the content for height
        color = Color(0xFF00947F),
        // color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight(500),
        fontFamily = FontFamily(Font(R.font.inter_medium))
    )
}

