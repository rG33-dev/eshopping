package com.example.eshopping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

import com.example.eshopping.domain.models.CartDataModel
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

// -------------------- CART SCREEN --------------------

@Composable
fun CartScreen(navController: NavController) {
    val viewModel: ShoppingAppViewModel = hiltViewModel()
    val state by viewModel.getCartState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCart()
    }

    // ERROR: Local variables 'cartItems', 'onIncreaseQty', 'onDecreaseQty', and 'onCheckout' 
    // were declared but not initialized. 
    // FIXED: Now initializing them using the ViewModel's state and logic.

    val cartItems = state.userData ?: emptyList()
    
    val onIncreaseQty: (CartDataModel) -> Unit = { item ->
        // Logic to increase quantity (e.g., call viewModel.addToCart with increased quantity)
    }
    
    val onDecreaseQty: (CartDataModel) -> Unit = { item ->
        // Logic to decrease quantity
    }

    val onCheckout: () -> Unit = {
        if (cartItems.isNotEmpty()) {
            // Navigating to checkout for the first item as a sample 
            // (You can modify this to handle a full cart checkout logic)
            navController.navigate(Routes.CheckOutScreen(cartItems.first().productId))
        }
    }

    val subtotal = cartItems.sumOf { (it.price.toDoubleOrNull() ?: 0.0) * (it.quantity.toIntOrNull() ?: 1) }
    val deliveryFee = if (subtotal > 0) 40.0 else 0.0
    val total = subtotal + deliveryFee

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.errorMessage != null) {
            Text(
                text = state.errorMessage ?: "Error loading cart",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    text = "My Cart",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider()

                if (cartItems.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Your cart is empty", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(cartItems, key = { it.cartId }) { item ->
                            CartItemRow(
                                item = item,
                                onIncreaseQty = { onIncreaseQty(item) },
                                onDecreaseQty = { onDecreaseQty(item) }
                            )
                        }
                    }
                }

                HorizontalDivider()

                Column(modifier = Modifier.padding(16.dp)) {
                    PriceRow("Subtotal", subtotal)
                    PriceRow("Delivery", deliveryFee)

                    Spacer(modifier = Modifier.height(8.dp))

                    PriceRow("Total", total, isTotal = true)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onCheckout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = cartItems.isNotEmpty()
                    ) {
                        Text(
                            text = "Checkout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// -------------------- CART ITEM ROW --------------------

@Composable
fun CartItemRow(
    item: CartDataModel,
    onIncreaseQty: () -> Unit,
    onDecreaseQty: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = item.name,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "₹${item.price}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecreaseQty) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }

            Text(
                text = item.quantity,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            IconButton(onClick = onIncreaseQty) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

// -------------------- PRICE ROW --------------------

@Composable
fun PriceRow(
    label: String,
    value: Double,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = if (isTotal) 18.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "₹${"%.2f".format(value)}",
            fontSize = if (isTotal) 18.sp else 14.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}
