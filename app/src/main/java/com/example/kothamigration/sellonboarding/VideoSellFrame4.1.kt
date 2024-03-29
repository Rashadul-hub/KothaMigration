package com.example.kothamigration.sellonboarding


import android.os.Handler
import android.os.Looper
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
import com.example.kothamigration.composablefunctions.FileUploadComponent
import com.example.kothamigration.composablefunctions.ReusableText
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import com.example.kothamigration.theme.TextStyleVariables
import com.example.kothamigration.utils.FileUploadStatus


@Composable
fun SelectUploadingVideo(onNextClicked: () -> Unit) {
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
                        SelectUploadingContent(dimensions,onNextClicked)
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
                SelectUploadingContent(dimensions,onNextClicked)
            }
        }
    }

}

@Composable
fun SelectUploadingContent(dimensions: Dimensions,onNextClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ) {
        Spacer(modifier = Modifier.height(dimensions.large))
        Spacer(modifier = Modifier.weight(0.5f))
        Spacer(modifier = Modifier.height(dimensions.small))


        FileUploadSelection()

        Spacer(modifier = Modifier.weight(1f))


        UploadingTextTitleContents(dimensions)


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



        VideoSelectionNextButton(onNextClicked)

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))
    }
}

@Composable
fun FileUploadSelection() {
    var uploadStatus by remember { mutableStateOf(FileUploadStatus.SELECT) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)


    ) {
        FileUploadComponent(
            onClick = {
                // Simulate a network call or file processing
                // Change the uploadStatus accordingly
                uploadStatus = FileUploadStatus.UPLOADING

                // Simulate a delay for processing
                Handler(Looper.getMainLooper()).postDelayed({
                    // Simulate an error during processing
                    // Change the uploadStatus accordingly
                    // uploadStatus = FileUploadStatus.ERROR

                    // Simulate a successful upload
                    // Change the uploadStatus accordingly
                    uploadStatus = FileUploadStatus.UPLOADED
                }, 2000)
            },
            buttonText = "Select File",
            status = FileUploadStatus.SELECT, ///Select Any variation
            caption = "Upload Main File",
            modifier = Modifier.align(CenterHorizontally) // Center horizontally


        )
    }

}


@Composable
fun VideoSelectionNextButton(onNextClicked: () -> Unit) {

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
            CustomButton(buttonText = "Next") {
                onNextClicked()

            }
        }
    }
}

@Composable
fun UploadingTextTitleContents(dimensions: Dimensions) {

    ReusableText(
        text = "Upload Video",
        style = TextStyleVariables.TitleStyle,
    )

    Spacer(modifier = Modifier.height(dimensions.smallMedium))

    ReusableText(
        text = "The video will only be available to user after purchase. ",
        style = TextStyleVariables.SubtitleStyle
    )



}


@Preview
@Composable
fun VideoSellFrameUploadingView() {
    SelectUploadingVideo{
    }
}