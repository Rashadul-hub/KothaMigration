/**
@Composable
fun CustomTextBox(
text: String,
onTextChange: (String) -> Unit,
inputType: TextInputType = TextInputType.Text,
isError: Boolean = false,
errorText: String = "",
isPhoneNumberInput: Boolean = false,
leadingIcon: Int? = null,
trailingIcon: Int? = null,
backgroundColor: Color = Color.White,
textColor: Color = Color.Black,
textStyle: TextStyle = TextStyle.Default,
borderColor: Color = Color.Gray,
caption: String = "",
darkMode: Boolean = false
) {
val bgColor = if (darkMode) Color.DarkGray else backgroundColor
val txtColor = if (darkMode) Color.LightGray else textColor

TextField(
value = text,
onValueChange = onTextChange,
isError = isError,
visualTransformation = if (isPhoneNumberInput) PhoneNumberVisualTransformation() else VisualTransformation.None,
keyboardOptions = KeyboardOptions.Default.copy(
keyboardType = when (inputType) {
TextInputType.Text -> KeyboardType.Text
TextInputType.Number -> KeyboardType.Number
TextInputType.Email -> KeyboardType.Email
TextInputType.Password -> KeyboardType.Password
TextInputType.Phone -> KeyboardType.Phone
TextInputType.LongText -> KeyboardType.Text
}
),
textStyle = textStyle.copy(
color = txtColor
),
colors = TextFieldDefaults.textFieldColors(
backgroundColor = bgColor,
textColor = txtColor,
focusedIndicatorColor = borderColor,
unfocusedIndicatorColor = borderColor,
disabledIndicatorColor = borderColor
),
leadingIcon = leadingIcon?.let {
{
Icon(
painter = painterResource(id = it),
contentDescription = "Leading Icon",
tint = txtColor,
modifier = Modifier.size(24.dp)
)
}
},
trailingIcon = trailingIcon?.let {
{
Icon(
painter = painterResource(id = it),
contentDescription = "Trailing Icon",
tint = txtColor,
modifier = Modifier.size(24.dp)
)
}
},
modifier = Modifier.fillMaxWidth(),
shape = RoundedCornerShape(4.dp),
singleLine = inputType != TextInputType.LongText,
maxLines = if (inputType == TextInputType.LongText) Int.MAX_VALUE else 1,
label = { Text(caption, color = txtColor) }
)

if (isError) {
Row(
verticalAlignment = Alignment.CenterVertically,
horizontalArrangement = Arrangement.spacedBy(4.dp),
modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
) {
Icon(
imageVector = Icons.Default.Error,
contentDescription = "Error",
tint = Color.Red,
modifier = Modifier.size(16.dp)
)
Text(
text = errorText,
color = Color.Red,
fontWeight = FontWeight.Bold
)
}
}
}

enum class TextInputType {
Text,
Number,
Email,
Password,
Phone,
LongText
}

@Composable
fun PhoneNumberVisualTransformation() = VisualTransformation {
val context = LocalContext.current
val phoneNumberFormatter = PhoneNumberFormat.getInstance(context)
val transformedText = rememberUpdatedState(phoneNumberFormatter.format(it))
TransformedText(transformedText.value)
}

@Composable
fun ExampleUsage() {
var text by remember { mutableStateOf("") }

CustomTextBox(
text = text,
onTextChange = { text = it },
inputType = TextInputType.Phone,
isError = false,
errorText = "Invalid phone number",
isPhoneNumberInput = true,
leadingIcon = R.drawable.mobile_icon,
trailingIcon = R.drawable.cancel_wizard,
backgroundColor = Color.White,
textColor = Color.Black,
textStyle = TextStyle.Default,
borderColor = Color.Gray,
caption = "Phone Number",
darkMode = false
)
}
 */


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.CustomInputBox
import com.example.kothamigration.composablefunctions.TextInputType

/**
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    inputType: TextInputType = TextInputType.SingleLineText,
    isError: Boolean = false,
    errorText: String = "",
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    textStyle: TextStyle = TextStyle.Default,
    borderColor: Color = Color.Gray,
    caption: String = "",
    darkMode: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val bgColor = if (darkMode) Color.DarkGray else backgroundColor
    val txtColor = if (darkMode) Color.LightGray else textColor

    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardType = when (inputType) {
        TextInputType.SingleLineText -> KeyboardType.Text
        TextInputType.Number -> KeyboardType.Number
        TextInputType.Email -> KeyboardType.Email
        TextInputType.Password -> KeyboardType.Password
        TextInputType.Phone -> KeyboardType.Phone
        TextInputType.LongText -> KeyboardType.Text
    }
    // Remember the updated interaction state
//    val borderColor by interactionSource.collectIsPressedAsState()


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        TextField(value = value,
            onValueChange = {
                onValueChange()
            },
            textStyle = textStyle.copy(color = txtColor),
            borderColor = if (borderColor) Color(0xFF00B99F) else MaterialTheme.colorScheme.outline,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = bgColor,
                textColor = txtColor,
                focusedIndicatorColor = borderColor,
                unfocusedIndicatorColor = borderColor,
                disabledIndicatorColor = borderColor
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            singleLine = inputType != TextInputType.LongText,
            maxLines = if (inputType == TextInputType.LongText) Int.MAX_VALUE else 1,
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "Leading Icon",
                        tint = txtColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            trailingIcon = trailingIcon?.let {
                {
                    Icon(painter = painterResource(id = it),
                        contentDescription = "Trailing Icon",
                        tint = txtColor,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onValueChange("")
                                keyboardController?.hide()
                            })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                })

        if (isError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = errorText, color = Color.Red, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
*/


/**
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    inputType: TextInputType = TextInputType.SingleLineText,
    isError: Boolean = false,
    errorText: String = "",
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    textStyle: TextStyle = TextStyle.Default,
    borderColor: Color = Color.Gray,
    caption: String = "",
    darkMode: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val bgColor = if (darkMode) Color.DarkGray else backgroundColor
    val txtColor = if (darkMode) Color.LightGray else textColor

    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardType = when (inputType) {
        TextInputType.SingleLineText -> KeyboardType.Text
        TextInputType.Number -> KeyboardType.Number
        TextInputType.Email -> KeyboardType.Email
        TextInputType.Password -> KeyboardType.Password
        TextInputType.Phone -> KeyboardType.Phone
        TextInputType.LongText -> KeyboardType.Text
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .background(bgColor)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            leadingIcon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "Leading Icon",
                    tint = txtColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            TextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = textStyle.copy(color = txtColor),
                borderColor = if (borderColor != Color.Transparent) Color(0xFF00B99F) else borderColor,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = bgColor,
                    textColor = txtColor,
                    focusedIndicatorColor = borderColor,
                    unfocusedIndicatorColor = borderColor,
                    disabledIndicatorColor = borderColor
                ),
                keyboardOptions = keyboardOptions.copy(keyboardType = keyboardType),
                keyboardActions = keyboardActions,
                singleLine = inputType != TextInputType.LongText,
                maxLines = if (inputType == TextInputType.LongText) Int.MAX_VALUE else 1,
                trailingIcon = {
                    if (trailingIcon != null) {
                        Icon(
                            painter = painterResource(id = trailingIcon),
                            contentDescription = "Trailing Icon",
                            tint = txtColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    onValueChange("")
                                    keyboardController?.hide()
                                }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        if (isError) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = Color.Red,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
*/

@Composable
fun Number1() {
    var phoneNumber by remember { mutableStateOf("") }

    CustomInputBox(
        label = "bKash Number",
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        borderColor = MaterialTheme.colorScheme.outline,
        isClearable = false,
        onClearClick = { phoneNumber = "" },
        leadingIcon = R.drawable.mobile_icon,
        textInputType = TextInputType.Phone,

    )
}
@Composable
fun Number2() {
    var phoneNumber by remember { mutableStateOf("") }

    CustomInputBox(
        label = "Reconfirm bKash Number",
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        borderColor = MaterialTheme.colorScheme.outline,
        isClearable = true,
        onClearClick = { phoneNumber = "" },
        leadingIcon = R.drawable.cancel_wizard,
        isLeadingIconStart = false,
        textInputType = TextInputType.Phone,


    )
}

@Composable
fun Number3() {
    var text by remember { mutableStateOf("") }

    CustomInputBox(
        label = "Seller Description",
        value = text,
        onValueChange = { text = it },
        borderColor = MaterialTheme.colorScheme.outline,
        isClearable = true,
        onClearClick = { text = "" },
        leadingIcon = R.drawable.cancel_wizard,
        isLeadingIconStart = false,
        textInputType = TextInputType.LongText,
        modifier = Modifier.height(230.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun TextView() {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp) // Adjust padding based on window size
            .wrapContentSize(Alignment.Center)
    ){


            Number1()

            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))



        Number2()


        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))

        Number3()


    }

}