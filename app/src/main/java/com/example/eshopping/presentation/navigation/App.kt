package com.example.eshopping.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.eshopping.R
import com.example.eshopping.presentation.screens.AllCategoriesScreenUI
import com.example.eshopping.presentation.screens.AppMallScreenUi
import com.example.eshopping.presentation.screens.CartScreen
import com.example.eshopping.presentation.screens.CategoryScreen
import com.example.eshopping.presentation.screens.CheckOutScreenUi
import com.example.eshopping.presentation.screens.EachCategoryProductScreenUi
import com.example.eshopping.presentation.screens.EachProductDetailScreenUi
import com.example.eshopping.presentation.screens.GetAllFav
import com.example.eshopping.presentation.screens.GetAllProducts
import com.example.eshopping.presentation.screens.HomeScreenUI
import com.example.eshopping.presentation.screens.LoginScreenUi
import com.example.eshopping.presentation.screens.MyOrderScreen
import com.example.eshopping.presentation.screens.PayScreen
import com.example.eshopping.presentation.screens.ProfileScreen
import com.example.eshopping.presentation.screens.SearchBarScreen
import com.google.firebase.auth.FirebaseAuth


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

    val navItems = listOf(
        BottomNavItem("Home", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("Category", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("My Order", painterResource(R.drawable.ic_launcher_foreground)),
        BottomNavItem("Mall", painterResource(R.drawable.ic_launcher_foreground))
    )

    val startNav = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
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
                                when(index) {
                                    0 -> navController.navigate(Routes.HomeScreen)
                                    1 -> navController.navigate(Routes.CategoryScreen)
                                    2 -> navController.navigate(Routes.MyOrderScreen)
                                    3 -> navController.navigate(Routes.MallScreen)
                                }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = startNav
            ) {

                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUi(navController = navController)
                    }
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUI(navController = navController)
                    }

                    composable<Routes.CategoryScreen> {
                        CategoryScreen(navController = navController)
                    }

                    composable<Routes.AppMallScreen> {
                        AppMallScreenUi(navController = navController)
                    }

                    composable<Routes.MyOrderScreen> {
                        MyOrderScreen(navController = navController)
                    }

                    composable<Routes.ProfileScreen> {
                        ProfileScreen(firebaseAuth = firebaseAuth, navController = navController)
                    }

                    composable<Routes.WishListScreen> {
                        GetAllFav(navController = navController)
                    }

                    composable<Routes.CartScreen> {
                        CartScreen(navController = navController)
                    }

                    composable<Routes.SearchScreen> {
                        SearchBarScreen(navController = navController)
                    }

                    composable<Routes.PayScreen> {
                        PayScreen(navController = navController)
                    }

                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProducts(navController = navController)
                    }

                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUI(navController = navController)
                    }

                    composable<Routes.EachProductDetailScreen> {
                        val product: Routes.EachProductDetailScreen = it.toRoute()
                        EachProductDetailScreenUi(
                            productID = product.productID,
                            navController = navController
                        )
                    }

                    composable<Routes.EachCategoryItemsScreen> {
                        val category: Routes.EachCategoryItemsScreen = it.toRoute()
                        EachCategoryProductScreenUi(
                            productID = category.categoryName,
                            navController = navController
                        )
                    }

                    composable<Routes.CheckoutScreen> {
                        val product: Routes.EachProductDetailScreen = it.toRoute() // Check route mapping
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
}

data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter? = null
)
