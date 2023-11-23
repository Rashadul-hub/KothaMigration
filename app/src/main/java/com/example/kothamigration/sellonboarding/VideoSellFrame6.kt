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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.composablefunctions.CustomInputBox
import com.example.kothamigration.composablefunctions.ReusableText
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import com.example.kothamigration.theme.TextStyleVariables
import com.example.kothamigration.utils.TextInputType

@Composable
fun PostCaptionScreen(
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
                        PostCaptionScreenContents(dimensions, onNextClicked)
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
                PostCaptionScreenContents(dimensions, onNextClicked)
            }
        }
    }

}

@Composable
fun PostCaptionScreenContents(dimensions: Dimensions, onNextClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ) {

        // Post Caption Text Field Box
        Post_Caption()


        // Video Title Text Field Box
        Video_Title_Optional()

        //Tags Text Field Box

        Tags_Optional()

        Spacer(modifier = Modifier.weight(0.1f))
        Spacer(modifier = Modifier.height(dimensions.small))
        Spacer(modifier = Modifier.height(dimensions.small))


        //Title & Subtitle
        SellFrame6TitleContents(dimensions)


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
        SellFrameButton6(onNextClicked)

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}


@Composable
fun Post_Caption() {
    var text by remember { mutableStateOf(" ") }

    CustomInputBox(
        caption = "Post caption",
        value = text,
        onValueChange = { text = it },
        onClearClick = { text = "" },
        textInputType = TextInputType.LongText,
    )
}


@Composable
fun Video_Title_Optional() {
    var phoneNumber by remember { mutableStateOf("") }

    CustomInputBox(
        caption = "Video title (Optional)",
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        onClearClick = { phoneNumber = "" },
        textInputType = TextInputType.SingleLineText,
    )
}


@Composable
fun Tags_Optional() {
    var text by remember { mutableStateOf("") }

    CustomInputBox(
        caption = "Tags (Optional)",
        value = text,
        onValueChange = { text = it },
        onClearClick = { text = "" },
        textInputType = TextInputType.SingleLineText,
    )
}



@Composable
fun SellFrameButton6(onNextClicked: () -> Unit) {
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
fun SellFrame6TitleContents(dimensions: Dimensions) {

    ReusableText(
        text = "Caption",
        style = TextStyleVariables.TitleStyle

    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    ReusableText(
        text = "Provide a caption for your post.",
        style = TextStyleVariables.SubtitleStyle

    )
}


@Preview(showBackground = true)
@Composable
fun PostCaptionScreenView() {
    PostCaptionScreen {

    }
}