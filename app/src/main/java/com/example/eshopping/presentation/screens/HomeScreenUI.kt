package com.example.eshopping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage

import com.example.eshopping.R
import com.example.eshopping.presentation.components.DetailedProductCard
import com.example.eshopping.presentation.components.DetailedProductItem
import com.example.eshopping.presentation.components.SearchBarComponentForHomeScreen
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeScreen.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Deliver to", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Current Location", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        IconButton(onClick = { /* Notifications */ }) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        }
                        IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        }
                    }
                }
                SearchBarComponentForHomeScreen(navController = navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            if (homeState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (homeState.errorMessage != null) {
                Text(
                    text = homeState.errorMessage ?: "Error",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Category Horizontal Row
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(homeState.categories ?: emptyList()) { category ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    navController.navigate(Routes.EachCategoryItemsScreen(category.name))
                                }
                            ) {
                                AsyncImage(
                                    model = "Add Image Here",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF0F0F0)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Add category Text Here", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    // Featured Banner (Placeholder for carousel)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        AsyncImage(
                            model = "https://img.freepik.com/free-vector/shopping-day-banner-template_23-2148679585.jpg",
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Products Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Flash Sale", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "See All",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { navController.navigate(Routes.SeeAllProductsScreen) }
                        )
                    }

                    // Product Grid (Using standard Column + Row or a height-constrained Grid)
                    // For a scrollable home screen, we use a custom grid-like layout within the column
                    val products = homeState.products
                    val chunks = products?.chunked(2)
                    
                    chunks?.forEach { rowProducts ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowProducts.forEach { product ->
                                DetailedProductCard(
                                    product = DetailedProductItem(
                                        image = R.drawable.ic_launcher_foreground, // Note: Card expects Int, model has URL
                                        productName = product.name,
                                        productPrice = "₹${product.price}",
                                        discountedPrice = "₹${product.finalPrice}",
                                        discount = "30% off",
                                        rating = "4.2",
                                        ratingCount = "(500)"
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            navController.navigate(Routes.EachProductDetailScreen(product.productId))
                                        }
                                )
                            }
                            if (rowProducts.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
