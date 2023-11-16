package com.example.kothamigration.sellonboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kothamigration.R
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
fun SellFrame1() {
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
        topBar = {},
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
                        SellFrame1Contents(dimensions)
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
                SellFrame1Contents(dimensions)
            }
        }
    }

}

@Composable
fun SellFrame1Contents(dimensions: Dimensions) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ) {
        Spacer(modifier = Modifier.height(dimensions.large))

        // Logo Section
        Image(
            painter = painterResource(id = R.drawable.ecommercecampaigncuate2),
            contentDescription = "Ecommerce campaign-cuate 2",
            modifier = Modifier
                .align(CenterHorizontally)
                .requiredSize(size = 300.dp)
        )

        Spacer(modifier = Modifier.weight(0.1f))
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensions.small))
        Spacer(modifier = Modifier.height(dimensions.small))

        TextContents(dimensions)


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

        Spacer(modifier = Modifier.height(dimensions.small))

        GetStartedButton()

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }




}

@Composable
fun GetStartedButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment =CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomButton(buttonText = "Get Started") {

            }
        }
    }
}

@Composable
fun TextContents(dimensions: Dimensions) {

//    Text(
//        text = "Earn",
//        textAlign = TextAlign.Center,
//        fontSize = 24.sp,
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight(),
//        color = MaterialTheme.colorScheme.secondary,
//        fontWeight = FontWeight(700),
//        fontFamily = FontFamily(Font(R.font.inter_bold)),
//    )
//
//    Spacer(modifier = Modifier.height(dimensions.smallMedium))
//
//    Text(
//        text = "Start earning by selling your digital contents or skills. ",
//        textAlign = TextAlign.Center,
//        fontSize = 18.sp,
//        overflow = TextOverflow.Ellipsis,
//        modifier = Modifier
//            .fillMaxWidth() //Take the full available width
//            .wrapContentHeight(),// Wrap the content for height
//        color = MaterialTheme.colorScheme.secondary,
//        fontWeight = FontWeight(300),
//        fontFamily = FontFamily(Font(R.font.inter_light)),
//    )
    ReusableText(
        text = "Earn",
        fontSize = 24,
        fontWeight = FontWeight(700),
        fontFamily = FontFamily(Font(R.font.inter_bold)),
    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    ReusableText(
        text = "Start earning by selling your digital contents or skills.",
        fontSize = 18,
        fontWeight = FontWeight(300),
        fontFamily = FontFamily(Font(R.font.inter_light)),
    )



}


@Preview
@Composable
fun SellFrameView() {
    SellFrame1()
}