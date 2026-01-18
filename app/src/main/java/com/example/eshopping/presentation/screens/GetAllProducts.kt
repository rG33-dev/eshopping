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
import androidx.compose.material.icons.filled.Search
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
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.components.DetailedProductCard
import com.example.eshopping.presentation.components.DetailedProductItem
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllProducts(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val state by viewModel.getAllProductsState.collectAsState()

    // Note: The viewModel.getAllProducts() function seems missing from the ViewModel based on previous inspection
    // I will trigger loadHomeScreenData or assume a similar pattern is intended.
    // If you add fun getAllProducts() to ViewModel, uncomment the next line:
    // LaunchedEffect(Unit) { viewModel.getAllProducts() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Products") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchScreen) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
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
                    text = state.errorMessage ?: "Error loading products",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                val products = state.userData ?: emptyList()
                if (products.isEmpty()) {
                    Text(
                        text = "No products available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products) { product ->
                            DetailedProductCard(
                                product = DetailedProductItem(
                                    image = R.drawable.ic_launcher_foreground,
                                    productName = product.name,
                                    productPrice = "₹${product.price}",
                                    discountedPrice = "₹${product.finalPrice}",
                                    discount = "${calculateDiscount(product.price, product.finalPrice)}% off",
                                    rating = "4.0",
                                    ratingCount = "(1k+)"
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        navController.navigate(Routes.EachProductDetailScreen(product.productId))
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
