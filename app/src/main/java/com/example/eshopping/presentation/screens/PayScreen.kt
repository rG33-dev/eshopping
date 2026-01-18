package com.example.eshopping.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(navController: NavController) {
    val viewModel: ShoppingAppViewModel = hiltViewModel()
    val checkoutState by viewModel.getCheckoutState.collectAsState()
    val cartState by viewModel.getCartState.collectAsState()
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedPaymentMethod by remember { mutableStateOf("UPI") }
    var isProcessing by remember { mutableStateOf(false) }

    // Calculate total amount based on whether it's a single product checkout or full cart
    val totalAmount = remember(checkoutState, cartState) {
        if (checkoutState.userData != null) {
            checkoutState.userData?.finalPrice?.toDoubleOrNull() ?: 0.0
        } else {
            cartState.userData?.sumOf { 
                (it.price.toDoubleOrNull() ?: 0.0) * (it.quantity.toIntOrNull() ?: 1) 
            } ?: 0.0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payments", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Total Amount Header
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Amount to Pay", fontSize = 14.sp, color = Color.Gray)
                            Text(
                                text = "₹${"%.2f".format(totalAmount)}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF4CAF50)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Payment Options", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))

                // Payment Methods
                PaymentOptionItem(
                    title = "UPI (Google Pay, PhonePe, BHIM)",
                    subtitle = "Pay directly from bank account",
                    isSelected = selectedPaymentMethod == "UPI",
                    onClick = { selectedPaymentMethod = "UPI" }
                )
                
                PaymentOptionItem(
                    title = "Credit / Debit / ATM Card",
                    subtitle = "Visa, Mastercard, RuPay & more",
                    isSelected = selectedPaymentMethod == "Card",
                    onClick = { selectedPaymentMethod = "Card" }
                )

                PaymentOptionItem(
                    title = "Net Banking",
                    subtitle = "All major Indian banks supported",
                    isSelected = selectedPaymentMethod == "NetBanking",
                    onClick = { selectedPaymentMethod = "NetBanking" }
                )

                PaymentOptionItem(
                    title = "Cash on Delivery",
                    subtitle = "Pay when you receive the order",
                    isSelected = selectedPaymentMethod == "COD",
                    onClick = { selectedPaymentMethod = "COD" }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Price Details Breakdown
                Text(text = "Price Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                
                PriceSummaryRow("Order Amount", "₹${"%.2f".format(totalAmount)}")
                PriceSummaryRow("Delivery Charges", "FREE", color = Color(0xFF4CAF50))
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                PriceSummaryRow("Total Payable", "₹${"%.2f".format(totalAmount)}", isBold = true)

                Spacer(modifier = Modifier.height(100.dp))
            }

            // Bottom Pay Button
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Button(
                    onClick = {
                        isProcessing = true
                        scope.launch {
                            delay(2000) // Simulate network delay
                            isProcessing = false
                            Toast.makeText(context, "Payment Successful!", Toast.LENGTH_LONG).show()
                            navController.navigate(Routes.HomeScreen) {
                                popUpTo(Routes.HomeScreen) { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                    enabled = !isProcessing
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = if (selectedPaymentMethod == "COD") "Confirm Order" else "Pay ₹${"%.2f".format(totalAmount)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentOptionItem(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFFF5722) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) Color(0xFFFFF3E0) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF5722))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PriceSummaryRow(label: String, value: String, isBold: Boolean = false, color: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(text = value, fontSize = 14.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal, color = color)
    }
}
