package com.example.eshopping.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProductDetailScreenUi(
    productID: String,
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val productState by viewModel.getProductByIdState.collectAsState()
    val addToCartState by viewModel.addToCartState.collectAsState()

    LaunchedEffect(key1 = productID) {
        viewModel.getProductById(productID)
    }

    LaunchedEffect(key1 = addToCartState.userData) {
        if (addToCartState.userData != null) {
            Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share logic */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
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
            if (productState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (productState.errorMessage != null) {
                Text(
                    text = productState.errorMessage ?: "Error",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            } else {
                val product = productState.userData
                if (product != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Image Section
                        AsyncImage(
                            model = product.image,
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .background(Color.White),
                            contentScale = ContentScale.Fit
                        )

                        Column(modifier = Modifier.padding(16.dp)) {
                            // Header: Name and Wishlist
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = product.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = { viewModel.addToFav(product) }) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "Add to Fav",
                                        tint = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Price Section
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "₹${product.finalPrice}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "₹${product.price}",
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${calculateDiscount(product.price, product.finalPrice)}% off",
                                    fontSize = 16.sp,
                                    color = Color(0xFF4CAF50),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))

                            // Description Section
                            Text(
                                text = "Product Description",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = product.description,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Category & Availability
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = "Category: ${product.category}", fontSize = 12.sp)
                                    Text(
                                        text = if (product.availableUnits > 0) "In Stock (${product.availableUnits} units)" else "Out of Stock",
                                        fontSize = 12.sp,
                                        color = if (product.availableUnits > 0) Color(0xFF4CAF50) else Color.Red
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(100.dp)) // Extra space for bottom bar
                        }
                    }

                    // Bottom Action Bar
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.addToCart(
                                    CartDataModel(
                                        productId = product.productId,
                                        name = product.name,
                                        price = product.finalPrice,
                                        image = product.image,
                                        quantity = "1",
                                        category = product.category,
                                        description = product.description
                                    )
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder
                        ) {
                            Text(text = "Add to Cart", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                navController.navigate(Routes.CheckOutScreen(product.productId))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text(text = "Buy Now", color = Color.White)
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
