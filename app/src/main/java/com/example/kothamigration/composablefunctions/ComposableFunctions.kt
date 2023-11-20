package com.example.kothamigration.composablefunctions


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.google.type.Money

enum class TextInputType {
    SingleLineText, Number, Email, Password, Phone, LongText,Money,Percentage
}

@Composable
fun InputBox(
    label: String,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    borderColor: Color,
    isClearable: Boolean,
    onClearClick: () -> Unit,
    leadingIcon: Int,
    isLeadingIconStart: Boolean = true,
    textInputType: TextInputType, // New parameter for input type
    modifier: Modifier = Modifier // New parameter for additional modifier


) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 11.62.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                fontWeight = FontWeight(500),
                letterSpacing = 0.4.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 2.dp, top = 4.dp)
            )

            // Input Number
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { onPhoneNumberChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 315.dp)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .then(modifier),
                keyboardOptions = when (textInputType) {
                    TextInputType.SingleLineText -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                    TextInputType.Number -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                    TextInputType.Email -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    )
                    TextInputType.Password -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    )
                    TextInputType.Phone -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    )
                    TextInputType.LongText -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                    TextInputType.Money -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                    TextInputType.Percentage -> KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start,
                    letterSpacing = 10.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                ),
                leadingIcon = {
                    if (isLeadingIconStart) {
                        if (isClearable) {
                            IconButton(onClick = { onClearClick() }) {
                                Icon(
                                    painter = painterResource(id = leadingIcon),
                                    contentDescription = "Clear",
                                    tint = Color(0xFF000000),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                },
                trailingIcon = {
                    if (!isLeadingIconStart) {
                        if (isClearable) {
                            IconButton(onClick = { onClearClick() }) {
                                Icon(
                                    painter = painterResource(id = leadingIcon),
                                    contentDescription = "Clear",
                                    tint = Color(0xFF000000),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CustomInputBox(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color,
    isClearable: Boolean,
    onClearClick: () -> Unit,
    leadingIcon:  Int,
    isLeadingIconStart: Boolean = true,
    textInputType: TextInputType,
    modifier: Modifier = Modifier // New parameter for additional modifier

) {
    InputBox(
        label = label,
        phoneNumber = value,
        onPhoneNumberChange = onValueChange,
        borderColor = borderColor,
        isClearable = isClearable,
        onClearClick = onClearClick,
        leadingIcon = leadingIcon,
        isLeadingIconStart = isLeadingIconStart,
        textInputType = textInputType,
        modifier=modifier
    )
}
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






@Preview
@Composable
fun ViewButton2() {
    CustomButton(buttonText = "DEMO", onClick = {})
}


@Preview(showBackground = true)
@Composable
fun Button() {
}
