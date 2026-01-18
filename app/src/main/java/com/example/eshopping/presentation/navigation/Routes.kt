package com.example.eshopping.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubNavigation(){
    @Serializable
    object LoginSignUpScreen : SubNavigation()
    @Serializable
    object MainHomeScreen : SubNavigation()
}
sealed class Routes{

    @Serializable
    object SignUpScreen
    @Serializable
    object AppMallScreen

    @Serializable
    object WishListScreen

    @Serializable
    object PayScreen

    @Serializable
    object SeeAllProductsScreen

    @Serializable
    object AllCategoriesScreen
    // In Routes.kt
    @Serializable
    data class EachProductDetailScreen(val productID: String) // Changed from object to data class
    @Serializable
    data class EachCategoryItemsScreen(val categoryName : String)

    @Serializable
    data class CheckOutScreen(val productID : String, )

    @Serializable
    object LoginScreen


    @Serializable
    object LoginSignUpScreen
    @Serializable
    object HomeScreen
    @Serializable
    object ProfileScreen
    @Serializable
    object SearchScreen

    @Serializable
    object CategoryScreen
    @Serializable
    object MyOrderScreen
    @Serializable
    object MallScreen
    @Serializable
    object CartScreen
    @Serializable
    object FavouriteScreen
    @Serializable
    object CheckoutScreen
    @Serializable
    object CheckoutSuccessScreen
    @Serializable
    object CheckoutFailedScreen
    @Serializable
    object CheckoutPaymentScreen
    @Serializable
    object CheckoutPaymentSuccessScreen
    @Serializable
    object CheckoutPaymentFailedScreen





}

@Serializable
data class CheckOutScreen(val productID : String)
@Serializable
data class CheckoutPaymentScreen(val productID : String)
@Serializable
data class CheckoutPaymentSuccessScreen(val productID : String)
@Serializable
data class CheckoutPaymentFailedScreen(val productID : String)
@Serializable
data class CheckoutSuccessScreen(val productID : String)
@Serializable
data class CheckoutFailedScreen(val productID : String)
@Serializable
data class HomeScreen(val productID : String)
@Serializable
data class ProfileScreen(val productID : String)
@Serializable
data class SearchScreen(val productID : String)
@Serializable
data class CartScreen(val productID : String)
@Serializable
data class FavouriteScreen(val productID : String)
@Serializable
data class LoginSignUpScreen(val productID : String)
@Serializable
data class Checkout(val productID : String)
