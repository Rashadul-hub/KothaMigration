package com.example.kothamigration.sellonboarding

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomButton
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import com.example.kothamigration.utils.TextBoxStatus

@Composable
fun Demo2() {
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
                        Contents(dimensions)
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
                Contents(dimensions)
            }
        }
    }

}

@Composable
fun Contents(dimensions: Dimensions) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensions.medium)
            .wrapContentSize(Alignment.Center)
    ) {

        // Gird View Selection

        //VideoCaption()
     //   UsableTextBox()
        Spacer(modifier = Modifier.weight(0.1f))

       // Choices()

        SelectionTitles(dimensions)


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

        ConfirmButton()

        Spacer(modifier = Modifier.height(dimensions.mediumLarge))

    }


}

/**
@Composable
fun Choices() {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        modifier = Modifier

            .width(300.dp)
            .height(140.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(Color.Transparent)

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

            .alpha(1f)


    ) {


        Text(
            text = "Upload Video",
            textAlign = TextAlign.Start,
            fontSize = 11.623046875.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4000000059604645.sp,
            lineHeight = 16.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

                .width(80.dp)

                //.height(16.dp)

                .alpha(1f),
            color = Color(red = 0.21568627655506134f, green = 0.27843138575553894f, blue = 0.30980393290519714f, alpha = 1f),
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            modifier = Modifier

                .width(300.dp)
                .height(120.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .background(
                    Color(
                        red = 0.9800000190734863f,
                        green = 0.9800000190734863f,
                        blue = 0.9800000190734863f,
                        alpha = 1f
                    )
                )
                .border(
                    1.dp,
                    Color(
                        red = 0.7166666388511658f,
                        green = 0.7166666388511658f,
                        blue = 0.7166666388511658f,
                        alpha = 1f
                    ),
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .padding(start = 20.dp, top = 32.dp, end = 20.dp, bottom = 32.dp)

                .alpha(1f)


        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()

                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Transparent)

                    .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                    .alpha(1f)


            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()

                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .background(Color.Transparent)

                        .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                        .alpha(1f)


                ) {

                    Box(


                        modifier = Modifier

                            .width(28.000001907348633.dp)
                            .height(28.000001907348633.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                            .background(Color.Transparent)

                            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                            .alpha(1f)


                    ) {

                        //ICon

                    }


                    Text(
                        text = "Select file",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.None,
                        letterSpacing = 0.20000000298023224.sp,
                        lineHeight = 22.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier

                            .fillMaxWidth()

                            //.height(22.dp)

                            .alpha(1f),
                        color = Color(red = 0.6196078658103943f, green = 0.6196078658103943f, blue = 0.6196078658103943f, alpha = 1f),
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                    )
                }
            }
        }
    }

}


@SuppressLint("RestrictedApi")
@Composable
fun VideoCaption() {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        modifier = Modifier

            .width(300.dp)
            .height(191.dp)
            .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(Color.Transparent)

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

            .alpha(1f)


    ) {


        Text(
            text = "Video caption",
            textAlign = TextAlign.Start,
            fontSize = 11.623046875.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.4000000059604645.sp,
            lineHeight = 16.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier

                .width(82.dp)

                //.height(16.dp)

                .alpha(1f),
            color = Color(red = 0.7019608020782471f, green = 0.14901961386203766f, blue = 0.11764705926179886f, alpha = 1f),
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
        )

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier

                .fillMaxWidth()
                .height(152.dp)
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
                .background(Color(red = 1f, green = 1f, blue = 1f, alpha = 1f))
                .border(1.dp, Color(red = 0.7019608020782471f, green = 0.14901961386203766f, blue = 0.11764705926179886f, alpha = 1f), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
                .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp)

                .alpha(1f)


        ) {


            Text(
                text = "Text input long long long long multiline multiline multiline multiline Text input long long long long multiline multiline multiline multiline Text input long long long long multiline multiline multiline multiline Text input long long long long end.",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier


                    .weight(1f)
                    //.fillMaxHeight()

                    .alpha(1f),
                color = Color(red = 0.18431372940540314f, green = 0.21568627655506134f, blue = 0.29411765933036804f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
            modifier = Modifier

                .width(254.dp)
                .height(15.dp)
                .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                .background(Color.Transparent)

                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

                .alpha(1f)


        ) {

            Canvas(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
                //.fillMaxWidth()
                //.aspectRatio(1f)

            ) {
                val fillPath = PathParser.createPathFromPathData("M 5 0 C 2.240000009536743 0 0 2.240000009536743 0 5 C 0 7.760000228881836 2.240000009536743 10 5 10 C 7.760000228881836 10 10 7.760000228881836 10 5 C 10 2.240000009536743 7.760000228881836 0 5 0 Z M 4.5 7.5 L 4.5 6.5 L 5.5 6.5 L 5.5 7.5 L 4.5 7.5 Z M 4.5 2.5 L 4.5 5.5 L 5.5 5.5 L 5.5 2.5 L 4.5 2.5 Z ")
                //fillPath.fillType = Path.FillType.EVEN_ODD
                val rectF = RectF()
                fillPath.computeBounds(rectF, true)
                val matrix = Matrix()
                val scale = minOf( size.width / rectF.width(), size.height / rectF.height() )
                matrix.setScale(scale, scale)
                fillPath.transform(matrix)
                val composePathFill = fillPath.asComposePath()

                drawPath(path = composePathFill, color = Color(red = 0.7019608020782471f, green = 0.14901961386203766f, blue = 0.11764705926179886f, alpha = 1f), style = Fill)
                drawPath(path = composePathFill, color = Color.Transparent, style = Stroke(width = 3f, miter = 4f, join = StrokeJoin.Round))
            }

            Text(
                text = "The text is too long. Please, make it short. The error text can only be 2 lines.",
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                textDecoration = TextDecoration.None,
                letterSpacing = 0.sp,

                overflow = TextOverflow.Ellipsis,
                modifier = Modifier

                    .width(238.dp)

                    .height(15.dp)

                    .alpha(1f),
                color = Color(red = 0.7019608020782471f, green = 0.14901961386203766f, blue = 0.11764705926179886f, alpha = 1f),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        }
    }

}
*/

//@Composable
//fun UsableTextBox() {
//    var text by remember { mutableStateOf("Hello") }
//
//    CustomTextBox(
//        text = text,
//        onTextChange = { text = it },
//        status = TextBoxStatus.Disabled,
//        isDarkMode = false
//    )
//
//}


@Composable
fun ConfirmButton() {
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
fun SelectionTitles(dimensions: Dimensions) {

    Text(
        text = "Select type",
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
        text = " What do you want to sell? ",
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
fun DemoView2() {
    Demo2()
}