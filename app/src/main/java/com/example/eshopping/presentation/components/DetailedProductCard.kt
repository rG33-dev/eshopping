package com.example.eshopping.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eshopping.R

@Composable
fun DetailedProductCard(
    product: DetailedProductItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            // Product Image + Wishlist Icon
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxWidth()
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground), // Replace with your heart icon
                        contentDescription = null,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {

                // Product Name
                Text(
                    text = product.productName,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Price Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.discountedPrice,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = product.productPrice,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = product.discount,
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Discount Applied Chip
                Box(
                    modifier = Modifier.background(
                        color = Color.Green.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(1.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Discount applied",
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground), // Replace with your discount icon
                            contentDescription = null,
                            modifier = Modifier.size(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Free Delivery
                Text(
                    text = "Free Delivery",
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Rating Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(width = 40.dp, height = 22.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF4CAF50) // Green color
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.rating,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_launcher_foreground), // Replace with your star icon
                                modifier = Modifier.size(10.dp),
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = product.ratingCount,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

data class DetailedProductItem(
    val image: Int,
    val productName: String,
    val productPrice: String,
    val discountedPrice: String,
    val discount: String,
    val rating: String,
    val ratingCount: String
)
