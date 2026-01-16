package com.example.eshopping.presentation.componentsimport

import androidx.annotation.DrawableRes
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eshopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCarousel() {
    data class CarouselItem(
        val id: Int,
        val contentDescription: String,
        @DrawableRes val imageId: Int
    )

    val items = remember {
        listOf(
            CarouselItem(0, "Banner 1", R.drawable.ic_launcher_background),
            CarouselItem(1, "Banner 2", R.drawable.ic_launcher_background),
            CarouselItem(2, "Banner 3", R.drawable.ic_launcher_background),
            CarouselItem(3, "Banner 4", R.drawable.ic_launcher_background),
        )
    }

    val state = rememberCarouselState { items.count() }

//    HorizontalMultiBrowseCarousel(
//        state = state,
//        preferredItemWidth = 300.dp,
//        itemSpacing = 8.dp,
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    )
//    { index ->
//        val item = items[index]
//        Image(
//            painter = painterResource(id = item.imageId),
//            contentDescription = item.contentDescription,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//    }

}