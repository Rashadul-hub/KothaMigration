package com.example.kothamigration.sellonboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions

@Composable
fun SellFrame2() {
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
                        SellFrame2Contents(dimensions)
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
                SellFrame2Contents(dimensions)
            }
        }
    }

}

@Composable
fun SellFrame2Contents(dimensions: Dimensions) {

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

        SellFrameButton()

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}


@Composable
fun bKashNumberBox() {
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
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()

        ) {


            Text(
                text = "bKash Number",
                fontSize = 11.62.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontWeight = FontWeight(500),
                letterSpacing = 0.4.sp,
                modifier = Modifier.padding(start = 2.dp, top = 4.dp)
            )


            //Input Number

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },

                modifier = Modifier
                    .fillMaxWidth() // Take the full available width within the Box
                    .widthIn(max = 315.dp) // maximum width
                    .border(
                        width = 1.dp,
                        color =  MaterialTheme.colorScheme.outline,// Inner Border Color
                        shape = RoundedCornerShape(size = 4.dp)

                    ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),


                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary,// Regular Black Color
                    textAlign = TextAlign.Center,
                    letterSpacing = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                ),



                leadingIcon = {

                    IconButton(onClick = { phoneNumber = " " })// Clear the text field on click
                    {

                        androidx.compose.material.Icon(
                            painter = painterResource(id = R.drawable.mobile_icon),
                            contentDescription = "Clear",
                            tint = Color(0xFF000000),
                            modifier = Modifier.size(24.dp)
                        )
                    }


                },

                )

        }
    }
}



@Composable
fun Re_confirmNumber() {
    var phoneNumber by remember { mutableStateOf("") }
    val isTextFieldEmpty by remember(phoneNumber) { derivedStateOf { phoneNumber.isEmpty() } }

    // Interaction source to track interactions with the TextField
    val interactionSource = remember { MutableInteractionSource() }
    // Remember the updated interaction state
    val borderColor by interactionSource.collectIsPressedAsState()


    Box(
        contentAlignment = Alignment.Center, // Center the content horizontally and vertically
        modifier = Modifier
            .fillMaxWidth() // Take the full available width
            .padding(horizontal = 16.dp) // Add horizontal padding
            .padding(vertical = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()

        ) {


            Text(
                text = "Reconfirm bKash Number",
                fontSize = 11.62.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontWeight = FontWeight(500),
                letterSpacing = 0.4.sp,
                modifier = Modifier.padding(start = 2.dp, top = 4.dp)
            )


            //Input Number

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },

                modifier = Modifier
                    .fillMaxWidth() // Take the full available width within the Box
                    .widthIn(max = 315.dp) // maximum width
                    .border(
                        width = 1.dp,
                        color = if (borderColor) Color(0xFF00B99F) else MaterialTheme.colorScheme.outline,// Inner Border Color
                        shape = RoundedCornerShape(size = 4.dp)

                    ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),

                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary,// Regular Black Color
                    textAlign = TextAlign.Center,
                    letterSpacing = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                ),

                interactionSource = interactionSource,

                trailingIcon = {
                    if (!isTextFieldEmpty) {
                        IconButton(onClick = { phoneNumber = " " })// Clear the text field on click
                        {

                            androidx.compose.material.Icon(
                                painter = painterResource(id = R.drawable.cancel_wizard),
                                contentDescription = "Clear",
                                tint = Color(0xFF000000),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }


                }

            )

        }
    }
}





@Composable
fun SellerDescription() {
    var strings by remember {
        mutableStateOf("")
    }

    // Interaction source to track interactions with the TextField
    val interactionSource = remember { MutableInteractionSource() }

    // Remember the updated interaction state
    val borderColor by interactionSource.collectIsPressedAsState()

    Box(
        contentAlignment = Alignment.Center, // Center the content horizontally and vertically
        modifier = Modifier
            .fillMaxWidth() // Take the full available width
            .padding(horizontal = 16.dp) // Add horizontal padding
            .padding(vertical = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Seller Description",
                fontSize = 11.62.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontWeight = FontWeight(500),
                letterSpacing = 0.4.sp,
                modifier = Modifier.padding(start = 2.dp, top = 4.dp)
            )

            //Input Seller Descriptions

            BasicTextField(
                value = strings,
                onValueChange = {
                    // Character Limit
                    if (it.length <= 60) {
                        strings = it

                    }
                },

                modifier = Modifier
                    .fillMaxWidth() // Take the full available width within the Box
                    .widthIn(max = 268.dp) // maximum width
                    .height(132.dp)
                    .verticalScroll(rememberScrollState())
                    .border(
                        width = 1.dp,
                        color = if (borderColor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,// Inner Border Color
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),


                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary,// Regular Black Color
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                ),
                interactionSource = interactionSource,


                )
        }
    }
}

@Composable
fun SellFrameButton() {
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

            }
        }
    }
}

@Composable
fun TitleContents(dimensions: Dimensions) {

    Text(
        text = "Register as seller",
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth() //Take the full available width
            .wrapContentHeight(),// Wrap the content for height
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_bold)),
    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    Text(
        text = " A bKash number is required. ",
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth() //Take the full available width
            .wrapContentHeight(),// Wrap the content for height
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight(300),
        fontFamily = FontFamily(Font(R.font.inter_light)),
    )
}


@Preview(showBackground = true)
@Composable
fun SellFrameView2() {
    SellFrame2()
}