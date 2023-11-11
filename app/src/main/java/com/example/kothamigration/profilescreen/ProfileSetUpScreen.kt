package com.example.kothamigration.profilescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.WindowSize


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetUpScreen(navController: NavController) {


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
                        navController.navigate("otp") //back to LanguageSelection Screen
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
                    SignInTitle(text = stringResource(id = R.string.me))
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
                        ProfileContent(dimensions, navController)
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
                ProfileContent(dimensions, navController)
            }
        }
    }
}

@Composable
fun ProfileContent(
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

        // Profile Picture Section
        ProfilePictureSelection()

        CheckButton(){
            navController.navigate("feed") // Navigate to the OTP screen

        }

    }


}

@Composable
fun CheckButton(
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(52.dp)
            .height(49.dp)
            .fillMaxWidth() // Take the full available width
            .clip(
                RoundedCornerShape(
                    topStart = 6.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(0.dp), // Remove default content padding
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Check",
            Modifier
                .size(32.dp)
                .padding(1.dp)
//                .align(Center)
            )
    }
}
/**
//@Composable
//fun CheckButton(
//    navController : NavController,
//    ) {
//    Box(
//
//        modifier = Modifier
//            .width(52.dp)
//            .height(49.dp)
//            .clip(
//                RoundedCornerShape(
//                    topStart = 0.dp,
//                    topEnd = 0.dp,
//                    bottomStart = 0.dp,
//                    bottomEnd = 0.dp
//                )
//            )
//            .background(Color.Transparent)
//            .alpha(1f)
//
//    ) {
//
//        Box(
//            modifier = Modifier
//                .width(52.dp)
//                .height(49.dp)
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 6.dp,
//                        topEnd = 16.dp,
//                        bottomStart = 16.dp,
//                        bottomEnd = 16.dp
//                    )
//                )
//                .align(Alignment.Center)
//                .background(
//                    Color(
//                        red = 0f,
//                        green = 0.7254902124404907f,
//                        blue = 0.6235294342041016f,
//                        alpha = 1f
//                    )
//                )
//        )
//        Icon(
//            imageVector = Icons.Default.Check,
//            contentDescription = "Check",
//            Modifier
//                .size(32.dp)
//                .padding(1.dp)
//                .align(Alignment.Center),
//
//        )
//    }
//
//}
**/

/** Composable Profile  Section **/
@Composable
fun ProfilePictureSelection() {

    Column(
        modifier = Modifier.padding(5.dp)


    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 126.dp)
                )
                .padding(1.dp)
                .width(126.dp)
                .height(122.dp)
                .align(CenterHorizontally)
        ) {
            //Profile Image here

        }

        Spacer(modifier = Modifier.height(20.dp))


        //Change Profile Picture Text / Option Here
        Text(
            text = "Change Profile Picture",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF00947F),
            )
        )


    }
}


@Preview
@Composable
fun ProfileView() {
    val dummyNavController = rememberNavController() // Create a dummy NavController
    ProfileSetUpScreen(navController = dummyNavController)
}