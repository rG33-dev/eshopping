package com.example.eshopping.presentation.navigation

import android.R.attr.name
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eshopping.R
import com.google.firebase.auth.FirebaseAuth
import java.lang.reflect.Modifier

@Composable
fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var shouldShowBottomBar by remember { mutableIntStateOf(0) }


    LaunchedEffect(currentDestination) {
        shouldShowBottomBar = when (currentDestination) {
            Routes.HomeScreen::class.qualifiedName,
            Routes.CategoryScreen::class.qualifiedName,
            Routes.MyOrderScreen::class.qualifiedName,
            Routes.MallScreen::class.qualifiedName -> 1

            else -> 0

        }
    }
    val BottomNavItem =


        listOf(
            BottomNavItem(
                name = "Home",
                icon = painterResource(R.drawable.ic_launcher_foreground), //Add home icon later
                unselectedIcon = painterResource(R.drawable.ic_launcher_foreground)
            ),


            BottomNavItem(
                name = "Category",
                icon = painterResource(R.drawable.ic_launcher_foreground), //Add category icon later
                unselectedIcon = painterResource(R.drawable.ic_launcher_foreground),

                ),
            BottomNavItem(
                name = "My Order",
                icon = painterResource(R.drawable.ic_launcher_foreground), //Add my order icon later
                unselectedIcon = painterResource(R.drawable.ic_launcher_foreground)
            ),
            BottomNavItem(
                name = "Mall",
                icon = painterResource(R.drawable.ic_launcher_foreground), //Add mall icon later
                unselectedIcon = painterResource(R.drawable.ic_launcher_foreground)
            )



        )
    var startScreen = if (firebaseAuth.currentUser == null)
    {
        SubNavigation.LoginSignUpScreen
    }
    else{
        SubNavigation.MainHomeScreen

    }

}



data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter? = null
) {

}



