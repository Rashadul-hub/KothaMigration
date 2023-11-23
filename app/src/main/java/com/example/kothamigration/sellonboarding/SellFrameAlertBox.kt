package com.example.kothamigration.sellonboarding


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.ButtonShort
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.loginscreen.PolicyContents
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.theme.TextStyleVariables


@Composable
fun SellFrameAlertBox(
    modifier: Modifier = Modifier, onNextClicked: () -> Unit,
) {


    val windowSize = rememberWindowSizeClass()
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
                        SellFrameAlertBoxContents(modifier)
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
                SellFrameAlertBoxContents(modifier)
            }
        }
    }
}

@Composable
fun SellFrameAlertBoxContents(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .requiredWidth(width = 300.dp)
            .requiredHeight(height = 400.dp)

            .wrapContentSize(Alignment.BottomCenter)
    ) {
        Column(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color(0xFF24DDBD)) //Color
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredWidth(width = 296.dp)
                    .padding(all = 24.dp)
                    .clip(shape = RoundedCornerShape(8.dp))

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mobile_withchecked),
                    contentDescription = "Icon",
                    tint = Color(0xff625b71),
                    modifier = Modifier.size(20.dp)

                )
                Text(
                    text = "Successfully posted.",
                    color = Color(0xff1d1b20), //Color
//                    textAlign = TextAlign.Start,
                    style = TextStyleVariables.TitleStyle,

                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Text(
                    text = "Share or promote your sell content. ",
                    color = Color(0xff49454f), //Color
                    style = TextStyleVariables.SubtitleStyle,
//                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(0.5f))

            }

        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .offset(x = 1.dp, y = 304.dp)
                .width(296.dp)
                .height(88.dp)
                .padding(horizontal = 16.dp, vertical = 24.dp)


        ) {
            ButtonShort(
                text = "Share",
                onClick = {
                    // Handle the click action
                })

            Spacer(modifier = Modifier.width(8.dp)) // Adjust the space between buttons

            ButtonShort(text = "Promote",
                onClick = {
                    // Handle the click action
                })
        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Transparent)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cancel_wizard),
                contentDescription = "Cancel",
                modifier = Modifier.size(24.dp)
            )
        }

    }
}


@Preview(showSystemUi = true)
@Composable
private fun DialogueBoxComponentPreview() {
    SellFrameAlertBox {}
}