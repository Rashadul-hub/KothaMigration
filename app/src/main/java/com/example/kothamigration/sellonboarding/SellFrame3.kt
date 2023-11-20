package com.example.kothamigration.sellonboarding


import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.Choice
import com.example.kothamigration.composablefunctions.ChoicesRow
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.ReusableText
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions

@Composable
fun SellFrame3() {
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
                        SellFrame3Contents(dimensions)
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
                SellFrame3Contents(dimensions)
            }
        }
    }

}

@Composable
fun SellFrame3Contents(dimensions: Dimensions) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium)
            .wrapContentSize(Alignment.Center)
    ) {

        // Gird View Selection
        ChoicesView()



        Spacer(modifier = Modifier.weight(0.1f))

        SellFrame3Titles(dimensions)

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

        NextButton()

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}
@Composable
fun ChoicesView() {
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            modifier = Modifier
                .align(TopCenter)
        ) {
            item {
                ChoicesRow(
                    choices = listOf(
                        Choice("Video", "Sell your video and get paid per download."),
                        Choice("Image", "Sell your photography or artwork and get paid per download."),
                    ),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it }
                )
            }
            item {
                ChoicesRow(
                    choices = listOf(
                        Choice("Skill", "Sell appointments for your time. You get paid for each booked time slot."),
                        Choice("Audio", "Sell your music and get paid per download."),
                    ),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it }
                )
            }
            item {
                ChoicesRow(
                    choices = listOf(
                        Choice("Pdf", "Sell your e-book, report or blog and get paid for each download."),
                        Choice("Text", "Sell coupons, promo code, poem and get paid per download."),
                    ),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it }
                )
            }
        }
    }
}



@Composable
fun NextButton() {
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
fun SellFrame3Titles(dimensions: Dimensions) {


    ReusableText(
        text = "Select type",
        fontSize = 24,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_bold)),
    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    ReusableText(
        text = "What do you want to sell?",
        fontSize = 18,
        fontWeight = FontWeight(300),
        fontFamily = FontFamily(Font(R.font.inter_light)),
    )


}


@Preview(showBackground = true)
@Composable
fun SellFrameView3() {
    SellFrame3()
}