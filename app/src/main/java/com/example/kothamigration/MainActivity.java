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
        composeBridge.callMyComposeScreen(composeView,new NavController(this));
    }
}