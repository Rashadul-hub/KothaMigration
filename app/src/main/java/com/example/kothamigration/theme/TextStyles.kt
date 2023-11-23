package com.example.kothamigration.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R

object TextStyleVariables{

    val TextBoxTitleStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight(500),
        letterSpacing = 0.4.sp,

        )

    val TextBoxInputStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.Start,

        )




    val NumberInputStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        letterSpacing = 2.sp,
        textAlign = TextAlign.Start,
    )

    val ErrorTextStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontSize = 12.sp,
        fontWeight = FontWeight(500),
    )



    val BoxTitleStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontSize = 18.sp,
        fontWeight = FontWeight(500),
    )
    val BoxSubtitleStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontSize = 11.sp,
        fontWeight = FontWeight(300),
    )
    val BoxTextStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontSize = 16.sp,
        lineHeight =22.4.sp,
        fontWeight = FontWeight(300),
        textAlign = TextAlign.Center,
        letterSpacing = 0.2.sp,
    )



    val ButtonLabelStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        textAlign = TextAlign.Center

    )


    val Text_Input_Longbox : TextStyle = TextStyle(

        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        textAlign = TextAlign.Start


        )


    val TitleStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontSize = 24.sp,
        fontWeight = FontWeight(700),
        textAlign = TextAlign.Center

    )
    val SubtitleStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_light)),
        fontSize = 18.sp,
        fontWeight = FontWeight(300),
        textAlign = TextAlign.Center

    )

    val CopyrightTextStyle : TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 10.sp,
        fontWeight = FontWeight(400),
    )





}