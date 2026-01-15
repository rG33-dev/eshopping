package com.example.eshopping.presentation.navigation

import android.R.attr.name
import android.text.Layout
import androidx.compose.animation.core.AnimationVector
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottombar.AnimatedBottomBar
import com.example.eshopping.R
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.google.firebase.auth.FirebaseAuth
import com.google.type.Color
import kotlin.collections.map
import  androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute


// Updated App.kt with fixes
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

    // Renamed to avoid shadowing the class name
    val navItems = listOf(
        BottomNavItem("Home", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("Category", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("My Order", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("Mall", painterResource(R.drawable.ic_launcher_foreground))
    )


    val startScreen = if (firebaseAuth.currentUser == null){
        SubNavigation.LoginSignUpScreen
    }
    else{
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (shouldShowBottomBar == 1) {
                NavigationBar {
                    navItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                // TODO: navController.navigate(Route)
                            },
                            icon = {
                                Icon(
                                    painter = navigationItem.icon,
                                    contentDescription = navigationItem.name
                                )
                            },
                            label = { Text(navigationItem.name) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Use innerPadding to automatically handle the bottom bar height
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.LoginSignUpScreen // Or your intended start screen
            ) {

                navigation<SubNavigation.LoginSignUpScreen>(startDestination = startScreen) {
                    composable<Routes.SignUpScreen> {
                        SignupScreenUi(navController = navController)
                    }
                }

                composable<Routes.HomeScreen> {
                    HomeScreenUI(navController = navController)
                }


                composable<Routes.CategoryScreen> {
                    Routes.CategoryScreen(navController = navController)
                }


                composable<Routes.AppMallScreen> {
                    Routes.AppMallScreen(navController = navController)
                }


                composable<Routes.MyOrderScreen> {
                    Routes.MyOrderScreen(navController = navController)
                }

                composable<Routes.ProfileScreen> {
                    Routes.ProfileScreen(firebaseAuth = firebaseAuth, navController = navController)
                }

                composable<Routes.WishListScreen> {
                    GetAllFav(
                        navController = navController
                    )

                }
                composable<Routes.CartScreen> {
                    Routes.CartScreen(navController = navController)
                }
                composable<Routes.SearchScreen> {
                    Routes.SearchScreen(navController = navController)
                }
                composable<Routes.PayScreen> {
                    Routes.PayScreen(navController = navController)
                }
                composable<Routes.SeeAllProductsScreen> {
                    Routes.GetAllProducts(navController = navController)
                }
                composable<Routes.AllCategoriesScreen> {
                    Routes.AllCategoriesScreen(navController = navController)
                }
                composable<Routes.AllCategoriesScreen> {
                    Routes.AllCategoriesScreen(navController = navController)
                }
                composable<Routes.EachProductDetailScreen> {
                    val product :  Routes.EachProductDetailScreen = it.toRoute()
                    EachProductDetailScreenUi(
                        productID = product.productID,
                        navController = navController
                    )
                }
                composable<Routes.EachCategoryItemsScreen> {
                    val product :  Routes.EachProductDetailScreen = it.toRoute()
                    EachCategoryProductScreenUi(
                        productID = product.productID,
                        navController = navController
                    )
                }
                composable<Routes.CheckoutScreen> {
                    val product :  Routes.EachProductDetailScreen = it.toRoute()
                   CheckOutScreenUi(
                        productID = product.productID,
                        navController = navController,
                        pay = payTest
                    )
                }




            }

        }
    }
}


data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter? = null
)



