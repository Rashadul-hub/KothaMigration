package com.example.kothamigration.composablefunctions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.app.darkMode
import com.example.kothamigration.utils.TextBoxStatus




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


// Seller Description Text Box For SellFrame 2
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


// Select Options For SellFrame 3
@Composable
fun ChoicesRow(
    choices: List<Choice>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        items(choices) { choice ->
            ChoiceItem(
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
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val backgroundColor = if (title == "Image") {
        if (isSelected) {
            Color(0xFF24DDBD) //Color
        } else {
            Color.White //Color
        }
    } else if (isSelected) {
        Color(0xFF24DDBD) //Color
    } else {
        Color(0x1F1D1B20)  //Color
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
            .clickable(onClick = onItemClick),

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
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.onSurface, //Color
                fontWeight = FontWeight(500),
                fontFamily = FontFamily(Font(R.font.inter_medium)) //Fonts
            )

            Text(
                text = subtitle,
                textAlign = TextAlign.Start,
                fontSize = 11.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .height(39.dp),
                color = MaterialTheme.colorScheme.onSurface, //Color
                fontWeight = FontWeight(300),
                fontFamily = FontFamily(Font(R.font.inter_light)) //Fonts
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

// Data Class For Choice -> SellFrame 3
data class Choice(val title: String, val subtitle: String)




