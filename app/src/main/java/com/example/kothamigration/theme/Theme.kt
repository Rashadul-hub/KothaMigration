package com.example.kothamigration.theme


import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.utils.ProvideAppUtils
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.WindowSizeClass
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.smallDimensions
import com.example.kothamigration.utils.LocalAppDimens
import com.example.kothamigration.utils.LocalOrientationMode
import com.example.kothamigration.utils.Orientation


private val LightColorPalette = lightColorScheme(

    primary = KothaGreen, // Green Apps Iconic Color
    onPrimary = Black2,  // REGULAR BLACK
    secondary = HeadingTextColor,// Heading Text Color
    onSecondary = Color.Black,
    error = RedErrorLight,
    background = White,// BackGround Color
    onBackground = Color.Black, // BackGround Color Text
    surface = Color.White,//TopAppBar Color
    onSurface = Black2,//TopAppBar Color Text
    tertiary= ResendButtonColor,
    outline = BoxBrder, //BoxBorder Color
    scrim = HintColorLight //Hint Text Color
)
private val DarkColorPalette = darkColorScheme(
    primary = KothaGreen,  // Green Apps Iconic Color
    onPrimary = Color.White,  // REGULAR WHITE
    secondary = Color.White,// Heading Text Color
    onSecondary = Color.White,
    error = RedErrorDark,
    background = Color.Black, // Black BackGround
    onBackground = Color.White,
    surface = Color.Black, //TopAppBar Color
    onSurface = Color.White, //TopAppBar Color Text
    outline = BoxBrder, //BoxBorder Color
    scrim = HintColorDark, // Hint Text Color
    tertiary= ResendButtonColorDark,



    )


@Composable
fun KothaTheme(

    windowSizeClass: WindowSizeClass, //Responsive Screen Size

    darkTheme: Boolean,
    //= isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {


    val colors = if (darkTheme){
        DarkColorPalette
    }else{
        LightColorPalette
    }





    //Optional , This Part helps You Set The Status-bar Color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.background.toArgb()

            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    //For Responsive Screen Size
    val orientation = when{
        windowSizeClass.width.size > windowSizeClass.height.size -> Orientation.Landscape
        else -> Orientation.Portrait
    }

    val sizeThatMatters = when(orientation){
        Orientation.Portrait -> windowSizeClass.width
        else -> windowSizeClass.height
    }

    val dimensions = when(sizeThatMatters){
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        else -> largeDimensions
    }

    val typography = when(sizeThatMatters){
        is WindowSize.Small -> typographySmall
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Medium -> typographyMedium
        else -> typographyBig
    }


    //AppUtils Class
    ProvideAppUtils(dimensions = dimensions, orientation = orientation) {

        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            content = content
        )
    }
}

object AppTheme{
    val dimens : Dimensions
        @Composable
        get() = LocalAppDimens.current

    val orientation : Orientation
        @Composable
        get() = LocalOrientationMode.current
}