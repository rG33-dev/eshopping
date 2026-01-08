package com.example.eshopping.presentation.Utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.eshopping.R
import com.example.eshopping.domain.models.BannerDataModels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Banner (banners: List<BannerDataModels>){
    val pagerState = rememberPagerState(pageCount = {banners.size})
    val scope = rememberCoroutineScope  ()

    LaunchedEffect(Unit) {
        while(true){
            delay(1500)
            val nextPage : Any = (pagerState.currentPage +1) % banners.size

            scope.launch {
                pagerState.animateScrollBy(nextPage as Float)
            }
        }



    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.wrapContentSize()
            ) { currentPage ->

                Card(
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 15.dp, end = 15.dp),
                    elevation = CardDefaults.elevatedCardElevation(8.dp)
                ) {
                    AsyncImage(
                        model = banners[currentPage].image,
                        contentDescription = banners[currentPage].name,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            }
        }

        PageIndicator(
            pageCount = banners.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
        )
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 3.dp)
    ) {
        repeat(pageCount) {
            IndicatorDot(
                isSelected = it == currentPage,
                modifier = modifier
            )
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun IndicatorDot(
    isSelected: Boolean,
    modifier: Modifier
) {
    if (isSelected) {
        SelectedDot(modifier)
    } else {
        Box(
            modifier = modifier
                .padding(2.dp)
                .clip(CircleShape)
                .size(8.dp)
                .background(
                    color =  Color(android.R.color.holo_orange_dark).copy(alpha = 0.5f),
                    shape = CircleShape
                )
        )
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun SelectedDot(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(2.dp)
            .height(10.dp)
            .width(28.dp)
            .background(
                color = Color(android.R.color.holo_orange_dark).copy(alpha = 0.8f),
                shape = RoundedCornerShape(5.dp)
            )
    )











}