package com.example.eshopping.presentation.navigation

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun App(firebaseAuth: FirebaseAuth,payTest: () -> Unit){
    val  navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val shouldShowBottomBar by remember { mutableIntStateOf(0) }


    LaunchedEffect(currentDestination) {
        shouldShowBottomBar =when(currentDestination){
            Routes.HomeScreen::class.qualifiedName,
            Routes.ProfileScreen::class.qualifiedName,
            Routes.My
        }
    }



}