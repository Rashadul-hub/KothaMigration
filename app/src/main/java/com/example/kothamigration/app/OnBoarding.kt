package com.example.kothamigration.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.kothamigration.model.OnBoardingContent
import com.example.kothamigration.sellonboarding.SellFrame1
import com.example.kothamigration.sellonboarding.SellFrame2

class OnBoarding : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {


                //OnBoardingContent()
            }
        }
    }

}

/** Previous java MAin Activity
 *
 * public class MainActivity extends AppCompatActivity {
 *
 *
 *     @Override
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *
 *         setContentView(R.layout.activity_main);
 *         ComposeView composeView = findViewById(R.id.myComposeView);
 *
 *         ComposeBridge composeBridge = new ComposeBridge();
 *         composeBridge.callMyComposeScreen(composeView,new NavController(this)); //calling The jetpack Compose Screens
 *
 *
 *
 *     }
 *
 * }
 *
 *
 *
 * **/