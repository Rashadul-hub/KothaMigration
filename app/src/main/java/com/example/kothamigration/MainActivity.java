package com.example.kothamigration;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.navigation.NavController;

import com.example.kothamigration.model.ComposeBridge;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        ComposeView composeView = findViewById(R.id.myComposeView);

        ComposeBridge composeBridge = new ComposeBridge();
        composeBridge.callMyComposeScreen(composeView,new NavController(this)); //calling The jetpack Compose Screens



    }

}


// Initialize the ViewModel
//        buttonTextViewModel = new ViewModelProvider(this).get(ButtonTextViewModel.class);
//
//        // Change the button text programmatically
//        buttonTextViewModel.setButtonText("New Button Text");

/***Another Example With MyComposable.Kt ***/

//
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.compose.ui.platform.ComposeView;
//
//import kotlin.Unit;
//import kotlin.jvm.functions.Function2;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ComposeView composeView = findViewById(R.id.myComposeView);
//
//
//        composeView.setContent((composer, integer) -> {
//            MyComposablesKt.MyComposeScreen((OnSubmitListener) text -> {
//                // Handle the text data from Composable here
//                System.out.println(text);
//            }, composer, integer);
//            return null;
//        });
//
//
//
//
//    }
//}
