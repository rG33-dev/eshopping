package com.example.eshopping.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.components.DetailedProductCard
import com.example.eshopping.presentation.components.DetailedProductItem
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllFav(
    navController: NavController

) {
    val viewModel: ShoppingAppViewModel = hiltViewModel()
    val state by viewModel.getAllFavState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllFav()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wishlist") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage ?: "An error occurred",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                val favProducts = state.userData ?: emptyList()
                if (favProducts.isEmpty()) {
                    Text(
                        text = "Your wishlist is empty!",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favProducts) { product ->
                            DetailedProductCard(
                                product = DetailedProductItem(
                                    image = R.drawable.ic_launcher_foreground, // Using placeholder as Card expects Int
                                    productName = product.name,
                                    productPrice = "₹${product.price}",
                                    discountedPrice = "₹${product.finalPrice}",
                                    discount = "${
                                        calculateDiscount(
                                            product.price,
                                            product.finalPrice
                                        )
                                    }% off",
                                    rating = "4.4", // Mocked
                                    ratingCount = "(85)" // Mocked
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        navController.navigate(
                                            Routes.EachProductDetailScreen(
                                                product.productId
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun calculateDiscount(price: String, finalPrice: String): Int {
    val p = price.toDoubleOrNull() ?: 0.0
    val f = finalPrice.toDoubleOrNull() ?: 0.0
    if (p == 0.0) return 0
    return (((p - f) / p) * 100).toInt()
}
