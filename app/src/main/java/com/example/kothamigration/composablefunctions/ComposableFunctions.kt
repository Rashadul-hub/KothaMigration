package com.example.kothamigration.composablefunctions


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.app.darkMode


//SignInTitle
@Composable
fun SignInTitle(text: String) {

    Text(
        text = text,

        style = TextStyle(
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            fontWeight = FontWeight(700),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    )
}

@Composable
 fun TitleText(text: String) {
      Text(
        text = text,
        style = TextStyle(
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            fontWeight = FontWeight(700),
            color = MaterialTheme.colorScheme.secondary,  // White Color
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    )
}


//Resend Button
@Composable
fun ResendButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(151.dp)
            .height(40.dp)
            .clip(
                RoundedCornerShape(5.dp)
            )
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.onPrimary, //Regular Black Color
                RoundedCornerShape(5.dp)
            )
            .background(
                MaterialTheme.colorScheme.tertiary
            )
            .clickable { onClick() } // Make it clickable
    ) {
        Text(
            text = buttonText,
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary, //Regular Black
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.inter_semi_bold))
        )
    }
}


///Send OTP Button


///Switch Theme Mode
@Composable
fun ThemeSwitch() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "DarkMode", fontSize = 30.sp)
            Spacer(modifier = Modifier.padding(16.dp))
            Switch(checked = darkMode, onCheckedChange = { darkMode = !darkMode })
        }

    }
}







@Preview
@Composable
fun ViewButton2() {
    CustomButton(buttonText = "DEMO", onClick = {})
}


@Preview(showBackground = true)
@Composable
fun Button() {
}
