package com.example.eshopping.presentation.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius

@Composable
fun LowPriceCard(cardData: lowPrice) {
    Column(
       verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(shape = RoundedCornerShape(4.dp)).background(Color.White)

        ) {
        Box(
            modifier = Modifier
                .size(width = 115.dp, height = 125.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFACF2E),
                            Color(0xFF8C0748)
                        )
                    ), shape = RoundedCornerShape(4.dp)
                ).padding(start =4.dp,top =4.dp, end =  6.dp, bottom =  6.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().shadow(elevation = 2.dp,shape = RoundedCornerShape(4.dp))
                    .size(width = 110.dp, height = 120.dp)
                    .clip(shape = RoundedCornerShape(4.dp)
                        ).background(Color.White)
            ) {
                Text(text = cardData.cardName, color =Color.Black)
            }
        }
    }


}

data class lowPrice(
    val image: Int,
    val price: String,
    val cardName: String
)

