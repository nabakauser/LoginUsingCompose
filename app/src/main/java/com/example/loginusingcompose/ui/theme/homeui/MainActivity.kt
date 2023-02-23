package com.example.loginusingcompose.ui.theme.homeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeViewModelRouter(homeViewModel = HomeViewModel())
        }
    }
}


