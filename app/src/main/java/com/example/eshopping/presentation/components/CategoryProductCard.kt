package com.example.eshopping.presentation.components

import android.R
import android.media.Image
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CategoryProductCard(product: CategoryProductItem) {
    Card(modifier = Modifier.width(100.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                painter = painterResource(id = product.image),
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(60.dp),
                contentDescription = "product image"
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.categoryProducutName,
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


data class CategoryProductItem(
    val image: Int,
    val categoryProducutName: String,
    val categoryName: String
)
