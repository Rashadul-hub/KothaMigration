package com.example.kothamigration.sellonboarding

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CardViewComponent
import com.example.kothamigration.composablefunctions.CheckboxComponent
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions

@Composable
fun VideoSellFrame8(
    onNextClicked: () -> Unit,
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
                        VideoSellFrame8Contents(dimensions, onNextClicked)
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
                VideoSellFrame8Contents(dimensions, onNextClicked)
            }
        }
    }

}

@Composable
fun VideoSellFrame8Contents(dimensions: Dimensions, onNextClicked: () -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ) {

        Spacer(modifier = Modifier.weight(0.1f))

        // Centering the CardViewDetails
        Column(
            modifier = Modifier

                .padding(top = 5.dp, bottom = 5.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            CardViewDetails()
        }

        Spacer(modifier = Modifier.height(dimensions.small))


        //Agreement Check Box
        AgreementCheckBox()


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

        //Next Button
        SellFrame8Button(onNextClicked)

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}


@Composable
fun CardViewDetails() {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(border = BorderStroke(0.10000000149011612.dp, Color(0xffb7b7b7))) //Border Color
            .padding(top = 5.dp, bottom = 5.dp)

//            .padding(vertical = 5.dp)

    ) {
        CardViewComponent(
            title = "Post Caption",

            text = "The dog is men’s best friend, so they say. But now technology" +
                    " has gone to such a level that there is no need for dogs, " +
                    "soon you will get a robot dog as your pet, " +
                    "which will act like a real dog.",

            linkText = "Read More",
            onLinkClick = {
                // Handle "Read More" click action
            }
        )
        CardViewComponent(
            title = "Tags",
            text = "#Wonderful",
        )
        CardViewComponent(
            title = "Main Video",
            linkText = "View",
            onLinkClick = {
                // Handle "View" click action
            }
        )
        CardViewComponent(
            title = "Preview Video",
            linkText = "View",
            onLinkClick = {
                // Handle "View" click action
            }
        )
        CardViewComponent(
            title = "Thumbnail",
            linkText = "View",
            onLinkClick = {
                // Handle "View" click action
            }
        )
        CardViewComponent(
            title = "Price BDT",
            text = "1200"
        )
    }
}

@Composable
fun AgreementCheckBox() {

    var isChecked by remember { mutableStateOf(false) }

    val updatedIsChecked = rememberUpdatedState(isChecked)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentSize(Alignment.BottomCenter)
            .clickable {
                isChecked = !updatedIsChecked.value
            }
    ) {
        CheckboxComponent(
            text = "I agree to all terms. I take full responsibility for the uploaded content. It doesn’t violate copyright and other laws.",
            isChecked = updatedIsChecked.value,
            onCheckedChange = { newChecked ->
                isChecked = newChecked
                // Handle the checkbox state change
            },
            modifier = Modifier.fillMaxWidth()
        )
    }


}

@Composable
fun SellFrame8Button(onNextClicked: () -> Unit) {
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
            CustomButton(buttonText = "Confirm and Post") {
                onNextClicked()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VideoSellFrame8View() {
    VideoSellFrame8 {

    }
}