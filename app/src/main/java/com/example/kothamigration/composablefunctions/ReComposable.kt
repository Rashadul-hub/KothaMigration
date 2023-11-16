package com.example.kothamigration.composablefunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R

//Custom KothaApp Button
@Composable
fun CustomButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = Modifier
            .width(307.dp) // Fixed width
            .height(55.dp)
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
        Text(
            text = buttonText,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            letterSpacing = (-0.3).sp,
            color = Color.White, // Button Text Color
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
            modifier = Modifier.align(Alignment.CenterVertically) // Align text vertically in the center
        )
    }
}


//For Title and SubTitle
@Composable
fun ReusableText(
    text: String,
    fontSize: Int,
    fontWeight: FontWeight,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
        .fillMaxWidth() //Take the full available width
        .wrapContentHeight(),// Wrap the content for height
    color: Color = MaterialTheme.colorScheme.secondary,
    textAlign: TextAlign = TextAlign.Center,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Text(
        text = text,
        textAlign = textAlign,
        fontSize = fontSize.sp,
        overflow = overflow,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = 0.4.sp
    )
}


//For SellFrame2
@Composable
fun PhoneNumberInputBox(
    label: String,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    borderColor: Color,
    isClearable: Boolean,
    onClearClick: () -> Unit,
    leadingIcon: Int,//ImageVector, // New parameter for leading icon
    isLeadingIconStart: Boolean = true, // New parameter to control icon position
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
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
                    ),

                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),

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
fun LongTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String = "",
    maxLines: Int = 5,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        ReusableText(
            text = label,
            fontSize = 12,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.inter_medium)), //Font
            modifier = Modifier.padding(start = 2.dp, top = 4.dp),
            color = if (isError) Color.Red else MaterialTheme.colorScheme.secondary  //Color
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .border(
                    width = 0.1.dp, color = if (isError) Color.Red else Color.Transparent //Color
                ),
            contentAlignment = Alignment.TopStart
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .border(
                        width = 0.1.dp,
                        color = if (isError) Color.Red else MaterialTheme.colorScheme.outline, //Color
                        shape = RoundedCornerShape(4.dp)
                    )
                    .verticalScroll(rememberScrollState()),

                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.secondary, //Color
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.inter_regular)) //Fonts
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                ),
                maxLines = maxLines,
                isError = isError
            )
        }



        /**Error Text Notification */
        if (isError) {
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),

                ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = Color.Red,
                    modifier = Modifier.size(10.dp)
                )
                ReusableText(
                    text = errorText,
                    textAlign = TextAlign.Start,
                    fontSize = 12,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = Color.Red,
                    modifier = Modifier.padding(start = 4.dp)// Adjust padding between icon and text

                )
            }
        }
    }
}
