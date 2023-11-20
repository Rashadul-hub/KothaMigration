package com.example.kothamigration.sellonboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.LongTextBox
import com.example.kothamigration.composablefunctions.PhoneNumberInputBox
import com.example.kothamigration.composablefunctions.ReusableText
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import com.example.kothamigration.utils.TextBoxStatus

@Composable
fun SellFrame2(
    onNextClicked : () -> Unit
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_wizard),
                        contentDescription = "Cancel",
                        Modifier
                            .size(24.dp)
                    )
                }
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
                        SellFrame2Contents(dimensions,onNextClicked)
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
                SellFrame2Contents(dimensions,onNextClicked)
            }
        }
    }

}

@Composable
fun SellFrame2Contents(dimensions: Dimensions,onNextClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ) {

        // Input bKash Number Section
        bKashNumberBox()


        // Reconfirm Number
        Re_confirmNumber()

        //Seller Description Box

        SellerDescription()

        Spacer(modifier = Modifier.weight(0.1f))
//        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensions.small))
        Spacer(modifier = Modifier.height(dimensions.small))

        TitleContents(dimensions)


        Spacer(modifier = Modifier.height(dimensions.medium))
        Spacer(modifier = Modifier.height(dimensions.medium))

        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            backgroundColor = Color(0xffeeeeee),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(8.dp)
                .align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(dimensions.smallMedium))

        SellFrameButton(onNextClicked)

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}

@Composable
fun bKashNumberBox() {
    var phoneNumber by remember { mutableStateOf("") }

    PhoneNumberInputBox(
        label = "bKash Number",
        phoneNumber = phoneNumber,
        onPhoneNumberChange = { phoneNumber = it },
        borderColor = MaterialTheme.colorScheme.outline,
        isClearable = true,
        onClearClick = { phoneNumber = "" },
        leadingIcon = R.drawable.mobile_icon,


        )
}

@Composable
fun Re_confirmNumber() {
    var phoneNumber by remember { mutableStateOf("") }
    val isTextFieldEmpty by remember(phoneNumber) { derivedStateOf { phoneNumber.isEmpty() } }

    // Interaction source to track interactions with the TextField
    val interactionSource = remember { MutableInteractionSource() }
    // Remember the updated interaction state
    val borderColor by interactionSource.collectIsPressedAsState()

    PhoneNumberInputBox(
        label = "Reconfirm bKash Number",
        phoneNumber = phoneNumber,
        onPhoneNumberChange = { phoneNumber = it },
        borderColor = if (borderColor) Color(0xFF00B99F) else MaterialTheme.colorScheme.outline,
        isClearable = !isTextFieldEmpty,
        onClearClick = { phoneNumber = "" },
        leadingIcon = R.drawable.cancel_wizard,// Use clear icon for Re_confirmNumber
        isLeadingIconStart = false // Set to false to move the icon to the right
    )
}


@Composable
fun SellerDescription() {
    var strings by remember { mutableStateOf("") }

    // Interaction source to track interactions with the TextField
    val interactionSource = remember { MutableInteractionSource() }

    // Remember the updated interaction state
    val borderColor by interactionSource.collectIsPressedAsState()

    LongTextBox(
        value = strings,
        onValueChange = { strings = it },
        label = "Seller Description",
        isError = strings.length > 40,
        errorText = "The text is too long. Please, make it short.Please, make it short.Please, make it short.Please, make it short.",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        maxLines = 10
    )
}

@Composable
fun SellFrameButton(onNextClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(buttonText = "Next") {
                onNextClicked()

            }
        }
    }
}

@Composable
fun TitleContents(dimensions: Dimensions) {

    ReusableText(
        text = "Register as seller",
        fontSize = 24,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_bold)),
    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    ReusableText(
        text = "A bKash number is required.",
        fontSize = 18,
        fontWeight = FontWeight(300),
        fontFamily = FontFamily(Font(R.font.inter_light)),
    )
}


@Preview(showBackground = true)
@Composable
fun SellFrameView2() {
//    SellFrame2()
}