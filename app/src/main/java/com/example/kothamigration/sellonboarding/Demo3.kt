package com.example.kothamigration.sellonboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.kothamigration.theme.TextStyleVariables
import com.example.kothamigration.theme.TextStyleVariables.NumberInputStyle
import com.example.kothamigration.theme.TextStyleVariables.TextBoxInputStyle
import com.example.kothamigration.theme.TextStyleVariables.Text_Input_Longbox
import com.example.kothamigration.utils.TextInputType


@Composable
fun InputBox1(
    caption: String,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    isClearable: Boolean? = null,
    onClearClick: () -> Unit,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    isLeadingIconStart: Boolean = true,
    textInputType: TextInputType,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    icons: @Composable () -> Unit = {}, // New parameter for icons
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(vertical = if (textInputType == TextInputType.SingleLineText || textInputType == TextInputType.LongText) 6.dp else 0.dp)
    ) {
        Text(
            text = caption,
            color = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, top = 4.dp),
            style = TextStyleVariables.TextBoxTitleStyle
        )

        val textFieldModifier = if (textInputType == TextInputType.LongText) {
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .widthIn(max = 315.dp)
                .padding(top = if (textInputType == TextInputType.SingleLineText) 0.dp else 6.dp)
        }

        val outlineTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF00B99F),
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
            cursorColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.secondary
        )

        val textStyle = when (textInputType) {
            TextInputType.SingleLineText -> TextBoxInputStyle.copy(MaterialTheme.colors.secondary)
            TextInputType.LongText -> Text_Input_Longbox.copy(MaterialTheme.colors.secondary)
            TextInputType.Slot, TextInputType.Number, TextInputType.Money, TextInputType.Percentage -> NumberInputStyle.copy(MaterialTheme.colors.secondary)
        }

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChange(it) },
            modifier = textFieldModifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = RoundedCornerShape(size = 4.dp)
                ),
            keyboardOptions = when (textInputType) {
                TextInputType.LongText -> KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
                else -> KeyboardOptions.Default.copy(
                    keyboardType = when (textInputType) {
                        TextInputType.Number -> KeyboardType.Number
                        TextInputType.Money, TextInputType.Percentage -> KeyboardType.Number
                        else -> KeyboardType.Text
                    }
                )
            },
            textStyle = textStyle,

            leadingIcon = {
                if (isLeadingIconStart && leadingIcon != null) {
                    if (isClearable == true) {
                        IconButton(onClick = { onClearClick() }) {
                            icons() // Invoke the icons lambda here
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.width(0.dp))
                }
            },
            trailingIcon = {
                if (!isLeadingIconStart && trailingIcon != null) {
                    if (isClearable == true) {
                        IconButton(onClick = { onClearClick() }) {
                            icons() // Invoke the icons lambda here
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.width(0.dp))
                }
            },

            colors = outlineTextFieldColors
        )
    }
}


@Composable
fun CustomInputBox2(
    caption: String,
    value: String,
    onValueChange: (String) -> Unit,
    isClearable: Boolean? = null,
    onClearClick: () -> Unit,

    showLeadingIcon: Boolean = false,
    leadingIcon: Int? = null,
    onLeadingIconClick: () -> Unit = {},


    showTrailingIcon: Boolean = false,

    trailingIcon: Int? = null,
    isLeadingIconStart: Boolean = true,
    onTrailingIconClick: () -> Unit = {},

    textInputType: TextInputType,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,



    ) {


    InputBox1(
        caption = caption,
        phoneNumber = value,
        onPhoneNumberChange = onValueChange,
        isClearable = isClearable,
        onClearClick = onClearClick,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isLeadingIconStart = isLeadingIconStart,
        textInputType = textInputType,
        modifier = modifier,
        style = style
    )


}



@Preview(showBackground = true)
@Composable
fun NVariation() {
    var phoneNumber by remember { mutableStateOf("") }

    CustomInputBox2(
        caption = "bKash Number",
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        isClearable = true,
        onClearClick = { phoneNumber = "" },
        leadingIcon = R.drawable.mobile_icon,
        textInputType = TextInputType.Number,
    )
}


