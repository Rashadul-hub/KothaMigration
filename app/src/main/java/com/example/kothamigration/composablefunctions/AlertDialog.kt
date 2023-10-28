package com.example.kothamigration.composablefunctions


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.kothamigration.R
//
//@Composable
//fun AlertDialog(navController: NavController) {
//
//    Dialog(onDismissRequest = { /*TODO*/ }) {
//        Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colors.background)
//        {
//            Box(
//                contentAlignment = Alignment.Center
//            ) {
//                Column(modifier = Modifier.padding(20.dp)) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Text(
//                            text = stringResource(id = R.string.error),
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                letterSpacing = 3.sp,
//                                fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
//                                fontWeight = FontWeight(700),
//                                color = Color.DarkGray
//                            )
//                        )
//                        Icon(
//                            imageVector = Icons.Filled.CancelPresentation,
//                            contentDescription = "",
//
//                            tint = Color.DarkGray,
//                            modifier = Modifier
//                                .width(20.dp)
//                                .height(20.dp)
//                                .clickable {
//
//                                    ///used FOr Demo Purpose
//                                    navController.navigate("terms")
//
//
//                                })
//
//
//                    }
//                    Spacer(modifier = Modifier.height(15.dp))
//
//                    Text(
//                        text = stringResource(id = R.string.invalid_action),
//                        style = TextStyle(
//                            fontSize = 15.sp,
//                            color = MaterialTheme.colors.error,
//                            fontFamily = FontFamily(Font(R.font.inter_regular)),
//                            fontWeight = FontWeight(500),
//
//                            )
//                    )
//                    Spacer(modifier = Modifier.height(15.dp))
//
//                    Box(
//                        modifier = Modifier.padding(
//                            start = 30.dp,
//                            end = 30.dp,
//                        )
//                    ) {
//
//                        CustomButton(buttonText = "Okay") {
//                            ///used FOr Demo Purpose
//                            navController.navigate("mode")
//                        }
//
//
//                    }
//
//                }
//
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun AlertDialogView() {
//    val dummyNavController = rememberNavController() // Create a dummy NavController
//    AlertDialog(navController = dummyNavController)
//}