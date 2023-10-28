package com.example.kothamigration.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



data class Dimensions(
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp
)



val smallDimensions = Dimensions(
    small = 20.dp,
    smallMedium = 25.dp,
    medium = 6.dp,
    mediumLarge = 40.dp,
    large = 46.dp
)

val compactDimensions = Dimensions(
    small = 30.dp,
    smallMedium = 35.dp,
    medium = 10.dp,
    mediumLarge = 45.dp,
    large = 55.dp
)

val mediumDimensions = Dimensions(
    small = 40.dp,
    smallMedium = 45.dp,
    medium = 15.dp,
    mediumLarge = 50.dp,
    large = 60.dp
)

val largeDimensions = Dimensions(
    small = 50.dp,
    smallMedium = 55.dp,
    medium = 20.dp,
    mediumLarge = 60.dp,
    large = 70.dp
)