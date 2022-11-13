
package com.example.pulstestapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pulstestapp.ui.NewsApp
import com.example.pulstestapp.ui.NewsViewModel
import com.example.pulstestapp.ui.screens.MainScreen
import com.example.pulstestapp.ui.theme.PulsTestAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PulsTestAppTheme {
                NewsApp()
            }
        }
    }
}

