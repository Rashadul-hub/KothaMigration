package com.example.kothamigration.composablefunctions

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.app.darkMode
import com.example.kothamigration.theme.TextStyleVariables
import com.example.kothamigration.theme.TextStyleVariables.NumberInputStyle
import com.example.kothamigration.theme.TextStyleVariables.TextBoxInputStyle
import com.example.kothamigration.theme.TextStyleVariables.Text_Input_Longbox
import com.example.kothamigration.utils.Choice
import com.example.kothamigration.utils.FileUploadStatus
import com.example.kothamigration.utils.TextInputType


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


//Custom KothaApp Button
@Composable
fun CustomButton(
    buttonText: String,
    onClick: () -> Unit = {},


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

            .background(MaterialTheme.colorScheme.primary)//color
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(0.dp), // Remove default content padding
    ) {
        Text(
            text = buttonText,
            color = Color.White, // Button Text Color
            style = TextStyleVariables.ButtonLabelStyle
        )
    }
}

@Composable
fun ButtonShort(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
        border = BorderStroke(0.2.dp, Color(0xFFB7B7B7)),//Color
        modifier = modifier
            .requiredWidth(width = 81.dp)
            .requiredHeight(height = 40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 81.dp)
                .requiredHeight(height = 40.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(height = 40.dp)
                    .background(color = Color(0xff24ddbd))
            ) {
                Text(
                    text = text,
                    fontSize=14.sp,
                    color = Color.Black, //Color
                    textAlign = TextAlign.Center,
                    lineHeight = 1.43.em,
                    letterSpacing = 0.1.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun ButtonShortPreview() {

}


/** Text Field with Variations**/
@Composable
fun ReusableText(
    text: String,
    fontSize: Int? = null,
    style: TextStyle = TextStyle.Default,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily? = null,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    color: Color? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow? = null,
) {
    Text(
        text = text,
        textAlign = textAlign ?: TextAlign.Center,
        fontSize = fontSize?.sp ?: style?.fontSize ?: 16.sp,
        overflow = overflow ?: TextOverflow.Ellipsis,
        modifier = modifier,
        color = color ?: MaterialTheme.colorScheme.secondary,
        fontWeight = fontWeight,
        fontFamily = fontFamily ?: style?.fontFamily ?: FontFamily.Default,
        letterSpacing = style?.letterSpacing ?: 0.4.sp,
        style = style,
    )
}


/** For Select an Types for Sell**/
@Composable
fun ChoicesRow(
    choices: List<Choice>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    style: TextStyle = TextStyle.Default,

    ) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        items(choices) { choice ->
            ChoiceItem(
                choice = choice,
                title = choice.title,
                subtitle = choice.subtitle,
                isSelected = selectedItem == choice.title,
                onItemClick = { onItemSelected(choice.title) }
            )
        }
    }
}

// Choice Items -> SellFrame 3
@Composable
fun ChoiceItem(
    choice: Choice,
    title: String,
    subtitle: String,
    style: TextStyle = TextStyle.Default,
    isSelected: Boolean,
    onItemClick: () -> Unit,
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val backgroundColor = if (choice.isEnabled) {
        if (isSelected) {
            Color(0xFF24DDBD)
        } else {
            Color.White //Color
        }
    } else {
        Color(0x1F1D1B20) //Color
    }


    val checkIconAlpha = if (isSelected) 1f else 0f

    val itemWidth = if (isLandscape) {
        250.dp // Adjust the width for landscape mode
    } else {
        165.dp // Default width for portrait mode
    }

    Box(
        modifier = Modifier
            .width(itemWidth)
            .height(100.dp)
            .border(0.1.dp, color = Color(0x2D1D1B20), shape = RoundedCornerShape(2.dp)) //Color
            .background(color = backgroundColor, shape = RoundedCornerShape(2.dp)) //Color
            .padding(8.dp)
            .clickable(onClick = onItemClick, enabled = choice.isEnabled),

        ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.onSurface, //Color
                style = TextStyleVariables.BoxTitleStyle,
            )

            Text(
                text = subtitle,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .height(39.dp),
                color = MaterialTheme.colorScheme.onSurface, //Color
                style = TextStyleVariables.BoxSubtitleStyle
            )
        }
        // White circular background for the icon
        Box(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
                .align(Alignment.TopEnd)
                .border(0.1.dp, Color.Black, CircleShape) //Color
                .background(color = Color.White, shape = CircleShape) //Color

        )

        // Check button in the top-right corner
        if (isSelected) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = Color(0xFF00B99F), //Color
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
                    .background(color = Color.White, shape = CircleShape) //Color
                    .alpha(checkIconAlpha)
            )

        }
    }
}


/** Input Text Fields **/
@Composable
fun InputBox(
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


    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(vertical = if (textInputType == TextInputType.SingleLineText || textInputType == TextInputType.LongText) 6.dp else 0.dp)
    ) {
        Text(
            text = caption,
            color = MaterialTheme.colorScheme.secondary,
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
                .padding(top = if (textInputType == TextInputType.SingleLineText) 0.dp else 6.dp) //new added

        }

        val outlineTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF00B99F),//Green Colort
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, //Color
            cursorColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.secondary
        )

        // Set different text styles based on input type
        val textStyle = when (textInputType) {
            TextInputType.SingleLineText -> TextBoxInputStyle.copy(MaterialTheme.colorScheme.secondary)
            TextInputType.LongText -> Text_Input_Longbox.copy(MaterialTheme.colorScheme.secondary)
            TextInputType.Slot, TextInputType.Number, TextInputType.Money, TextInputType.Percentage -> NumberInputStyle.copy(
                MaterialTheme.colorScheme.secondary
            )
            // Set more your desired text style for Password input type

        }

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChange(it) },
            modifier = textFieldModifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
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

                            androidx.compose.material.Icon(
                                painter = painterResource(id = leadingIcon),
                                contentDescription = "leading Icon",
                                tint = Color(0xFF000000), //Color For LeadingIcon
                                modifier = Modifier.size(24.dp)
                            )
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
                            androidx.compose.material.Icon(
                                painter = painterResource(id = trailingIcon),
                                contentDescription = "Clear",
                                tint = Color(0xFF000000),
                                modifier = Modifier.size(24.dp)
                            )
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
fun CustomInputBox(
    caption: String,
    value: String,
    onValueChange: (String) -> Unit,
    isClearable: Boolean? = null,
    onClearClick: () -> Unit,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    isLeadingIconStart: Boolean = true,

    textInputType: TextInputType,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
) {


    InputBox(
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


/** File Uploading Fields **/
fun getBorderColor(status: FileUploadStatus): Color {
    return when (status) {
        FileUploadStatus.UPLOADING -> Color(0xFF00B99F) // Green for uploaded
        FileUploadStatus.ERROR -> Color(0xFFB3261E) // Red for error
        else -> Color(0xffb7b7b7) // Default grey color
    }
}

fun getImageResource(status: FileUploadStatus): Int {
    return when (status) {
        FileUploadStatus.UPLOADING -> R.drawable.uploading_icon // Customize with your icon resources
        FileUploadStatus.ERROR -> R.drawable.error_outline// Customize with your error icon resource
        else -> R.drawable.uploading_icon // Default icon resource
    }
}

fun getImageColor(status: FileUploadStatus): Color {
    return when (status) {
        FileUploadStatus.ERROR -> Color(0xFFB3261E) // Red for error
        FileUploadStatus.UPLOADING -> Color(0xFF00B99F) // Green for uploading
        else -> Color(0xff9e9e9e) // Default color
    }
}

fun getButtonText(status: FileUploadStatus): String {
    return when (status) {
        FileUploadStatus.UPLOADING -> "Uploading.." // Customize with your uploading text
        FileUploadStatus.UPLOADED -> "File Uploaded. Tap to view"
        FileUploadStatus.ERROR -> "Upload Failed/Wrong file type"
        FileUploadStatus.SELECT -> "Select file"
        FileUploadStatus.PROCESSING -> "Processing.."
    }
}

fun getTextColor(status: FileUploadStatus): Color {
    return when (status) {
        FileUploadStatus.ERROR -> Color(0xFFB3261E) // Red for error
        else -> Color(0xff9e9e9e) // Default color
    }
}

@Composable
fun FileUploadComponent(
    onClick: () -> Unit,
    buttonText: String,
    status: FileUploadStatus,
    caption: String,
    style: TextStyle = TextStyle.Default,
    // Added parameter for custom TextStyle

    modifier: Modifier = Modifier,
) {


    // Dynamic color for caption based on status
    val captionColor =
        if (status == FileUploadStatus.ERROR) Color(0xFFB3261E) else Color(0xff37474f)


    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.height(150.dp)
    ) {
        androidx.compose.material.Text(
            text = caption,
            color = captionColor, //caption color Variable
            lineHeight = 1.33.em,
            style = TextStyleVariables.TextBoxTitleStyle //Custom Styles
        )
        SelectFilePanel(
            onClick = onClick,
            buttonText = buttonText,
            status = status,
            style = style,
            modifier = Modifier
        )
    }
}

@Composable
fun SelectFilePanel(
    onClick: () -> Unit,
    buttonText: String,
    status: FileUploadStatus,
    style: TextStyle = TextStyle.Default,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = Color(0xfffafafa)) // White Textbox Fill Color
            .border(
                border = BorderStroke(1.dp, getBorderColor(status)),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 20.dp, vertical = 32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .requiredWidth(width = 300.dp) //Fix Width
                .requiredHeight(height = 140.dp) //Fix Height
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(size = 28.dp)
            ) {
                Image(
                    painter = painterResource(id = getImageResource(status)),
                    contentDescription = "Group",
                    colorFilter = ColorFilter.tint(getImageColor(status)),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 2.332763671875.dp, y = 2.33331298828125.dp)
                        .requiredSize(size = 23.dp)
                )
            }


            androidx.compose.material.Text(
                text = getButtonText(status),
                color = getTextColor(status),
                lineHeight = 8.75.em,
                style = TextStyleVariables.BoxTextStyle, ///Custom Text Style
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}


/** Check Box**/
@Composable
fun CheckboxComponent(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth() // Take full width
            .padding(8.dp)    // Add padding
    ) {

        IconButton(
            onClick = { },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp)) // Adjust the corner radius
                .background(color = Color(0xFFE6EEED)) //Circular Box Color
                .size(38.dp) // Adjust the size
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp) // Adjust the inner box size
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(4.dp) // Adjust the padding
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { onCheckedChange(it) },

                        )
                }
            }
        }

        androidx.compose.material.Text(
            text = text,
            color = Color.Black,
            style = TextStyleVariables.CopyrightTextStyle,// Adjust the font size
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

/**Card View Details */
@Composable
fun CardViewComponent(
    text: String? = null,
    title: String,
    linkText: String? = null,
    linkColor: Color = Color(0xff00a98d),
    onLinkClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE


    val itemWidth = if (isLandscape) {
        400.dp// Adjust the width for landscape mode
    } else {
        260.dp
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
        modifier = modifier
            .width(itemWidth)
            .fillMaxWidth() // Make the column take the full width of its parent
//            .background(color = Color.White)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        androidx.compose.material.Text(
            text = title,
            color = Color(0xff37474f),
            style = TextStyleVariables.TextBoxTitleStyle
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
            modifier = Modifier
                .fillMaxWidth() // Make the inner column take the full width
                .padding(vertical = 5.dp)
        ) {
            if (text != null) {
                androidx.compose.material.Text(
                    text = text,
                    color = Color(0xff6e6e6e),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontWeight = FontWeight(400)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() // Adjust height as needed
                )
            }
            if (linkText != null) {
                androidx.compose.material.Text(
                    text = linkText,
                    color = linkColor,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontWeight = FontWeight(400)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() // Adjust height as needed
                        .clickable { onLinkClick() }
                )
            }
        }
    }
}



